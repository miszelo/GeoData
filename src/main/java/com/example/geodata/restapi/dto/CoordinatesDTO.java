package com.example.geodata.restapi.dto;

import lombok.Builder;

@Builder
public record CoordinatesDTO(double longitude,
                             double latitude) {
}
