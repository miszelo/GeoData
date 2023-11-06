package com.example.geodata.repository;

import com.example.geodata.model.City;
import com.example.geodata.model.Coordinates;
import com.example.geodata.model.GeoData;
import com.example.geodata.model.Place;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RepositoryUtils {
    static Coordinates getCoordinates(double longitude, double latitude) {
        return Coordinates.builder()
                .longitude(longitude)
                .latitude(latitude)
                .build();
    }

    static City getCity(String name) {
        return City.builder()
                .name(name)
                .build();
    }

    static Place getPlace(String name, String street, String postalCode, City city, Coordinates coordinates) {
        return Place.builder()
                .name(name)
                .street(street)
                .postalCode(postalCode)
                .city(city)
                .coordinates(coordinates)
                .build();
    }

    static GeoData getGeoData(Place place, LocalDateTime timestamp, BigDecimal humidity, BigDecimal temperature, BigDecimal pressure, BigDecimal pm10, BigDecimal pm25) {
        return GeoData.builder()
                .place(place)
                .timestamp(timestamp)
                .humidity(humidity)
                .temperature(temperature)
                .pressure(pressure)
                .pm10(pm10)
                .pm25(pm25)
                .build();
    }
}
