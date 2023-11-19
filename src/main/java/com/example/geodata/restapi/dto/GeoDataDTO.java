package com.example.geodata.restapi.dto;

import lombok.Builder;

import java.math.BigDecimal;


@Builder
public record GeoDataDTO(PlaceDTO place,
                         String timestamp,
                         BigDecimal humidity,
                         BigDecimal pressure,
                         BigDecimal temperature,
                         BigDecimal pm10,
                         BigDecimal pm25) {
}
