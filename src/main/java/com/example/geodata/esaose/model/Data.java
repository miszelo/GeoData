package com.example.geodata.esaose.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Data {

    @JsonProperty("humidity_avg")
    private double humidityAverage;

    @JsonProperty("pressure_avg")
    private double pressureAverage;

    @JsonProperty("temperature_avg")
    private double temperatureAverage;

    @JsonProperty("pm10_avg")
    private double pm10Average;

    @JsonProperty("pm25_avg")
    private double pm25Average;

}
