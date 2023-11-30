package com.example.geodata.restapi.dto;

import com.example.geodata.model.Place;

import java.time.LocalDateTime;

public interface GeoDataByDateRangeDTO {

    LocalDateTime getDate();

    PlaceDTO getPlace();

    Double getHumidity();

    Double getPressure();

    Double getTemperature();

    Double getPm10();

    Double getPm25();
}