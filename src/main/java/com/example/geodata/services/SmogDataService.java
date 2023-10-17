package com.example.geodata.services;

import com.example.geodata.esaose.EsaOseDataRetriever;
import com.example.geodata.model.GeoData;
import com.example.geodata.repository.CoordinatesRepository;
import com.example.geodata.repository.GeoDataRepository;
import com.example.geodata.repository.PlaceRepository;
import com.example.geodata.translators.EsaOseSmogDataResponseTranslator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
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
                           GeoDataRepository geoDataRepository,
                           PlaceRepository placeRepository,
                           CoordinatesRepository coordinatesRepository) {
        this.esaOseSmogDataResponseTranslator = esaOseSmogDataResponseTranslator;
        this.esaOseDataRetriever = esaOseDataRetriever;
        this.geoDataRepository = geoDataRepository;
        this.placeRepository = placeRepository;
        this.coordinatesRepository = coordinatesRepository;
    }

    public List<GeoData> saveData() {
        var esaOseData = esaOseDataRetriever.getEsaOseData();
        var geoData = esaOseData.getSmogDataList().stream()
                .map(esaOseSmogDataResponseTranslator::translate)
                .toList();
        if (geoDataRepository.findFirstByTimestamp(geoData.get(0).getTimestamp()).isPresent()) {
            return Collections.emptyList();
        }
        for (var data : geoData) {
            var place = data.getPlace();
            var coordinates = place.getCoordinates();
            var existingCoordinates = coordinatesRepository.findFirstByLongitudeAndLatitude(
                    coordinates.getLongitude(),
                    coordinates.getLatitude());
            var existingPlace = placeRepository.findFirstByCoordinates(existingCoordinates.orElse(null));
            if (existingPlace.isPresent() && existingCoordinates.isPresent()) {
                data.setPlace(existingPlace.get());
            }
        }
        geoDataRepository.saveAll(geoData);
        return geoData;
    }

    public List<GeoData> getGeoData() {
        return geoDataRepository.findAllByTimestampAfter(
                        LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))
                .orElse(Collections.emptyList());
    }
}
