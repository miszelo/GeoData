package com.example.geodata.restapi.mappers;

import com.example.geodata.model.Place;
import com.example.geodata.restapi.dto.CoordinatesDTO;
import com.example.geodata.restapi.dto.PlaceDTO;

public class PlaceMapper {
    public static PlaceDTO mapPlaceToPlaceDTO(Place place) {
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
