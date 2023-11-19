package com.example.geodata.restapi.mappers;

import com.example.geodata.model.GeoData;
import com.example.geodata.restapi.dto.GeoDataDTO;

import java.util.List;

import static com.example.geodata.restapi.mappers.PlaceMapper.mapPlaceToPlaceDTO;

public class GeoDataMapper {
    public static List<GeoDataDTO> mapGeoDataToGeoDataDTO(List<GeoData> a) {
        return a.stream()
                .map(geoData -> GeoDataDTO.builder()
                        .place(mapPlaceToPlaceDTO(geoData.getPlace()))
                        .pm10(geoData.getPm10())
                        .pm25(geoData.getPm25())
                        .humidity(geoData.getHumidity())
                        .pressure(geoData.getPressure())
                        .temperature(geoData.getTemperature())
                        .timestamp(geoData.getTimestamp().toString())
                        .build()).toList();
    }
}
