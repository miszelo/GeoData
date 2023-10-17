package com.example.geodata.restapi.dto;

import lombok.Builder;

@Builder
public record GeoDataDTO(PlaceDTO place,
                         String timestamp,
                         java.math.BigDecimal humidity,
                         java.math.BigDecimal pressure,
                         java.math.BigDecimal temperature,
                         java.math.BigDecimal pm10,
                         java.math.BigDecimal pm25) {
}
