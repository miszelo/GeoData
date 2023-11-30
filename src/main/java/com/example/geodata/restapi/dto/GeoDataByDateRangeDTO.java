package com.example.geodata.restapi.dto;

import com.example.geodata.model.Place;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@RequiredArgsConstructor
public class GeoDataByDateRangeDTO {
    private final Place place;
    private final Date timestamp;
    private final double humidity;
    private final double pressure;
    private final double temperature;
    private final double pm10;
    private final double pm25;
}