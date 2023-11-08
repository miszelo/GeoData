package com.example.geodata.utils;

import com.example.geodata.esaose.model.Data;
import com.example.geodata.esaose.model.EsaOseData;
import com.example.geodata.esaose.model.School;
import com.example.geodata.esaose.model.SmogData;

import java.time.LocalDateTime;
import java.util.List;

public class ServiceUtils {

    public static EsaOseData getEsaOseData(List<SmogData> smogDataList) {
        return EsaOseData.builder()
                .smogDataList(smogDataList)
                .build();
    }

    public static SmogData getSmogData(Data data, School school, LocalDateTime timestamp) {
        return SmogData.builder()
                .data(data)
                .school(school)
                .timestamp(timestamp)
                .build();
    }

    public static LocalDateTime getTimestamp(String timestamp) {
        return LocalDateTime.parse(timestamp);
    }

    public static School getSchool(String city, String name, String street, String postalCode, double latitude, double longitude) {
        return School.builder()
                .city(city)
                .name(name)
                .street(street)
                .latitude(latitude)
                .longitude(longitude)
                .postalCode(postalCode)
                .build();
    }

    public static Data getData(double pm10Average, double pm25Average, double humidityAverage, double pressureAverage, double temperatureAverage) {
        return Data.builder()
                .pm10Average(pm10Average)
                .pm25Average(pm25Average)
                .humidityAverage(humidityAverage)
                .pressureAverage(pressureAverage)
                .temperatureAverage(temperatureAverage)
                .build();
    }

}
