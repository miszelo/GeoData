package com.example.geodata.services;

import com.example.geodata.esaose.EsaOseDataRetriever;
import com.example.geodata.model.Coordinates;
import com.example.geodata.model.GeoData;
import com.example.geodata.model.Place;
import com.example.geodata.repository.GeoDataRepository;
import com.example.geodata.repository.PlaceRepository;
import com.example.geodata.restapi.dto.GeoDataDTO;
import com.example.geodata.translators.EsaOseSmogDataResponseTranslator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

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
        var geoDataSet = new HashSet<Coordinates>();
        var geoData = esaOseData.getSmogDataList().stream()
                .map(esaOseSmogDataResponseTranslator::translate)
                .filter(filterRepeatedValues(geoDataSet))
                .toList();
        if (geoDataRepository.findFirstByTimestamp(geoData.get(0).getTimestamp()).isPresent()) {
            return Collections.emptyList();
        }

        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            filterExistingRecords(geoData);
            for (int i = 0; i < geoData.size(); i++) {
                int finalI = i;
                executorService.submit(() -> {
                    geoDataRepository.save(geoData.get(finalI));
                });
            }
        }
        return mapGeoDataToGeoDataDTO(geoData);
    }


    @Cacheable("currentGeoData")
    public List<GeoDataDTO> getCurrentGeoData() {
        var esaOseData = esaOseDataRetriever.getEsaOseData();
        var geoDataSet = new HashSet<Coordinates>();
        var geoData = esaOseData.getSmogDataList().stream()
                .map(esaOseSmogDataResponseTranslator::translate)
                .filter(filterRepeatedValues(geoDataSet))
                .toList();
        return mapGeoDataToGeoDataDTO(geoData);
    }


    @Cacheable("geoDataByDay")
    public List<GeoDataDTO> getGeoDataByDay(LocalDate localDate) {
        Timestamp sqlTimestamp = Timestamp.valueOf(localDate.atStartOfDay());
        var geoData = geoDataRepository.findAllByTimestamp(sqlTimestamp);
        return mapGeoDataToGeoDataDTO(geoData);
    }

    @Cacheable("geoDataBySchoolNameAndDate")
    public List<GeoDataDTO> getGeoDataBySchoolNameAndDate(String schoolName, LocalDate localDate) {
        Timestamp sqlTimestamp = Timestamp.valueOf(localDate.atStartOfDay());
        var geoData = geoDataRepository.findAllBySchoolNameAndTimestamp(schoolName, sqlTimestamp)
                .orElseThrow();
        return mapGeoDataToGeoDataDTO(geoData);
    }

    @Cacheable("geoDataByCityAndDate")
    public List<GeoDataDTO> getGeoDataByCityAndDate(String city, LocalDate localDate) {
        Timestamp sqlTimestamp = Timestamp.valueOf(localDate.atStartOfDay());
        var geoData = geoDataRepository.findAllByCityAndTimestamp(city, sqlTimestamp)
                .orElseThrow();
        return mapGeoDataToGeoDataDTO(geoData);
    }

    private Predicate<GeoData> filterRepeatedValues(HashSet<Coordinates> geoDataSet) {
        return smogData -> {
            Coordinates coordinates = smogData.getPlace().getCoordinates();
            boolean isDuplicate = !geoDataSet.add(coordinates);
            return !isDuplicate;
        };
    }

    private void filterExistingRecords(List<GeoData> geoData) {
        List<Place> places = placeRepository.findAll();

        Map<String, Place> placesMap = new HashMap<>();
        places.forEach(place -> placesMap.put(place.getCoordinates().getLatitude() + "," + place.getCoordinates().getLongitude(), place));

        geoData.forEach(data -> {
            String key = data.getPlace().getCoordinates().getLatitude() + "," + data.getPlace().getCoordinates().getLongitude();
            Place place = placesMap.get(key);
            if (place != null) {
                data.setPlace(place);
            }
        });
    }

}
