package com.example.geodata.esaoseapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Data {

    private Double humidity_avg;

    private Double pressure_avg;

    private Double temperature_avg;

    private Double pm10_avg;

    private Double pm25_avg;

    @Override
    public String toString() {
        return "Data{" +
                "humidity_avg=" + humidity_avg +
                ", pressure_avg=" + pressure_avg +
                ", temperature_avg=" + temperature_avg +
                ", pm10_avg=" + pm10_avg +
                ", pm25_avg=" + pm25_avg +
                '}';
    }
}
