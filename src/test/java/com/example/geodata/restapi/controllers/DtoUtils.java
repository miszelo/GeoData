package com.example.geodata.restapi.controllers;

import com.example.geodata.restapi.dto.CoordinatesDTO;
import com.example.geodata.restapi.dto.GeoDataDTO;
import com.example.geodata.restapi.dto.PlaceDTO;

import java.math.BigDecimal;

public class DtoUtils {
    public static CoordinatesDTO getCoordinatesDto(double longitude, double latitude) {
        return CoordinatesDTO.builder()
                .longitude(longitude)
                .latitude(latitude)
                .build();
    }


    public static PlaceDTO getPlaceDto(String name, String street, String postalCode, String city, CoordinatesDTO coordinatesDTO) {
        return PlaceDTO.builder()
                .name(name)
                .street(street)
                .postalCode(postalCode)
                .city(city)
                .coordinates(coordinatesDTO)
                .build();
    }

    public static GeoDataDTO getGeoDataDto(PlaceDTO placeDTO, String timestamp, BigDecimal humidity, BigDecimal temperature, BigDecimal pressure, BigDecimal pm10, BigDecimal pm25) {
        return GeoDataDTO.builder()
                .place(placeDTO)
                .timestamp(timestamp)
                .humidity(humidity)
                .temperature(temperature)
                .pressure(pressure)
                .pm10(pm10)
                .pm25(pm25)
                .build();
    }
}
