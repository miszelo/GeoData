package com.example.geodata.services;

import com.example.geodata.esaose.EsaOseDataRetriever;
import com.example.geodata.model.GeoData;
import com.example.geodata.model.Place;
import com.example.geodata.repository.GeoDataRepository;
import com.example.geodata.repository.PlaceRepository;
import com.example.geodata.restapi.dto.CoordinatesDTO;
import com.example.geodata.restapi.dto.GeoDataDTO;
import com.example.geodata.restapi.dto.PlaceDTO;
import com.example.geodata.translators.EsaOseSmogDataResponseTranslator;
import jakarta.transaction.Transactional;
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
    public List<GeoData> saveData() {
        var esaOseData = esaOseDataRetriever.getEsaOseData();
        var geoData = esaOseData.getSmogDataList().stream()
                .map(esaOseSmogDataResponseTranslator::translate)
                .toList();
        if (geoDataRepository.findFirstByTimestamp(geoData.get(0).getTimestamp()).isPresent()) {
            return Collections.emptyList();
        }

        geoData.forEach(data -> {
            var place = data.getPlace();
            var existingPlace = placeRepository.findFirstByNameAndStreetAndPostalCode(
                    place.getName(),
                    place.getStreet(),
                    place.getPostalCode()
            );
            existingPlace.ifPresent(data::setPlace);
        });

        geoDataRepository.saveAll(geoData);
        return geoData;
    }

    public List<GeoDataDTO> getCurrentGeoData() {
        var a = geoDataRepository.findAllByTimestampAfter(
                LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        return a.stream()
                .map(geoData -> GeoDataDTO.builder()
                        .place(mapPlaceToPlaceDTO(geoData.getPlace()))
                        .timestamp(geoData.getTimestamp().toString())
                        .humidity(geoData.getHumidity())
                        .pressure(geoData.getPressure())
                        .temperature(geoData.getTemperature())
                        .pm10(geoData.getPm10())
                        .pm25(geoData.getPm25())
                        .build()).toList();
    }

    private PlaceDTO mapPlaceToPlaceDTO(Place place) {
        return PlaceDTO.builder()
                .name(place.getName())
                .street(place.getStreet())
                .postalCode(place.getPostalCode())
                .city(place.getCity().getName())
                .coordinates(CoordinatesDTO.builder()
                        .latitude(place.getCoordinates().getLatitude())
                        .longitude(place.getCoordinates().getLongitude())
                        .build())
                .build();
    }
}
