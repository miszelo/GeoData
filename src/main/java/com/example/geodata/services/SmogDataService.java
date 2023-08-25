package com.example.geodata.services;

import com.example.geodata.esaose.EsaOseDataRetriever;
import com.example.geodata.model.GeoData;
import com.example.geodata.repository.CoordinatesRepository;
import com.example.geodata.repository.GeoDataRepository;
import com.example.geodata.repository.PlaceRepository;
import com.example.geodata.translators.EsaOseSmogDataResponseTranslator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmogDataService {

    private final EsaOseSmogDataResponseTranslator esaOseSmogDataResponseTranslator;
    private final EsaOseDataRetriever esaOseDataRetriever;
    private final GeoDataRepository geoDataRepository;
    private final PlaceRepository placeRepository;
    private final CoordinatesRepository coordinatesRepository;

    public SmogDataService(EsaOseSmogDataResponseTranslator esaOseSmogDataResponseTranslator,
                           EsaOseDataRetriever esaOseDataRetriever,
                           GeoDataRepository geoDataRepository, PlaceRepository placeRepository,
                           CoordinatesRepository coordinatesRepository) {
        this.esaOseSmogDataResponseTranslator = esaOseSmogDataResponseTranslator;
        this.esaOseDataRetriever = esaOseDataRetriever;
        this.geoDataRepository = geoDataRepository;
        this.placeRepository = placeRepository;
        this.coordinatesRepository = coordinatesRepository;
    }

    public List<GeoData> getGeoData() {
        var esaOseData = esaOseDataRetriever.getEsaOseData();
        var geoData = esaOseData.getSmogDataList().stream()
                .map(esaOseSmogDataResponseTranslator::translate)
                .toList();
        if (geoDataRepository.findFirstByTimestamp(geoData.get(0).getTimestamp()).isPresent()) {
            System.out.println("juz takie dane sa");
            return List.of();
        }
        geoData.forEach(this::saveGeoDataToDatabase);
        return geoData;
    }

    private void saveGeoDataToDatabase(GeoData geoData) {
        var existingCoordinates = coordinatesRepository.findFirstByLongitudeAndLatitude(
                geoData.getPlace().getCoordinates().getLongitude(),
                geoData.getPlace().getCoordinates().getLatitude());
        var existingPlace = placeRepository.findFirstByNameAndCityAndStreetAndPostalCode(
                geoData.getPlace().getName(),
                geoData.getPlace().getCity(),
                geoData.getPlace().getStreet(),
                geoData.getPlace().getPostalCode());
        if (existingPlace.isPresent() && existingCoordinates.isPresent()) {
            geoData.setPlace(existingPlace.get());
        }
        geoDataRepository.save(geoData);
    }
}
