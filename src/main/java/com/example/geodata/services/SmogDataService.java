package com.example.geodata.services;

import com.example.geodata.esaose.EsaOseDataRetriever;
import com.example.geodata.model.GeoData;
import com.example.geodata.model.Place;
import com.example.geodata.repository.GeoDataRepository;
import com.example.geodata.repository.PlaceRepository;
import com.example.geodata.restapi.dto.GeoDataDTO;
import com.example.geodata.translators.EsaOseSmogDataResponseTranslator;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.geodata.restapi.mappers.GeoDataMapper.mapGeoDataToGeoDataDTO;

@Service
public class SmogDataService {

    private final EsaOseSmogDataResponseTranslator esaOseSmogDataResponseTranslator;
    private final EsaOseDataRetriever esaOseDataRetriever;
    private final GeoDataRepository geoDataRepository;
    private final PlaceRepository placeRepository;

    public SmogDataService(EsaOseSmogDataResponseTranslator esaOseSmogDataResponseTranslator,
                           EsaOseDataRetriever esaOseDataRetriever,
                           GeoDataRepository geoDataRepository,
                           PlaceRepository placeRepository) {
        this.esaOseSmogDataResponseTranslator = esaOseSmogDataResponseTranslator;
        this.esaOseDataRetriever = esaOseDataRetriever;
        this.geoDataRepository = geoDataRepository;
        this.placeRepository = placeRepository;
    }

    @Transactional
    public List<GeoDataDTO> saveData() {
        var esaOseData = esaOseDataRetriever.getEsaOseData();
        var geoData = esaOseData.getSmogDataList().stream()
                .map(esaOseSmogDataResponseTranslator::translate)
                .toList();
        if (geoDataRepository.findFirstByTimestamp(geoData.get(0).getTimestamp()).isPresent()) {
            return Collections.emptyList();
        }

        filterExistingRecords(geoData);

        geoDataRepository.saveAll(geoData);

        return mapGeoDataToGeoDataDTO(geoData);
    }

    private void filterExistingRecords(List<GeoData> geoData) {
        List<Place> places = placeRepository.findAll();

        Map<String, Place> placesMap = new HashMap<>();
        places.forEach(place ->
                placesMap.put(place.getCoordinates().getLatitude() + "," + place.getCoordinates().getLongitude(), place));

        geoData.forEach(data -> {
            String key = data.getPlace().getCoordinates().getLatitude() + "," + data.getPlace().getCoordinates().getLongitude();
            Place place = placesMap.get(key);
            if (place != null) {
                data.setPlace(place);
            }
        });
    }

    @Cacheable("currentGeoData")
    public List<GeoDataDTO> getCurrentGeoData() {
        var a = geoDataRepository.findAllByTimestampAfter(
                LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        return mapGeoDataToGeoDataDTO(a);
    }

}
