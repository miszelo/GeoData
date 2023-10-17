package com.example.geodata.restapi.dto;

import lombok.Builder;

@Builder
public record PlaceDTO(String name,
                       String street,
                       String postalCode,
                       String city,
                       CoordinatesDTO coordinates) {
}
