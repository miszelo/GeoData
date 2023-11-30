package com.example.geodata.restapi.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GeoDataByDateRangeDTOImpl {

    private final LocalDateTime timestamp;
    private final PlaceDTO place;
    private final Double humidity;
    private final Double pressure;
    private final Double temperature;
    private final Double pm10;
    private final Double pm25;


    public GeoDataByDateRangeDTOImpl(LocalDateTime timestamp,
                                     String name,
                                     String street,
                                     String postalCode,
                                     String city,
                                     Double longitude,
                                     Double latitude,
                                     Double humidity,
                                     Double pressure,
                                     Double temperature,
                                     Double pm10,
                                     Double pm25) {
        this.timestamp = timestamp;
        this.place = PlaceDTO.builder()
                .city(city)
                .street(street)
                .name(name)
                .postalCode(postalCode)
                .coordinates(CoordinatesDTO.builder()
                        .longitude(longitude)
                        .latitude(latitude)
                        .build())
                .build();
        this.humidity = humidity;
        this.pressure = pressure;
        this.temperature = temperature;
        this.pm10 = pm10;
        this.pm25 = pm25;
    }
}
