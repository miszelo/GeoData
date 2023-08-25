package com.example.geodata.translators;

import com.example.geodata.esaose.model.School;
import com.example.geodata.esaose.model.SmogData;
import com.example.geodata.model.Coordinates;
import com.example.geodata.model.GeoData;
import com.example.geodata.model.Place;
import org.springframework.stereotype.Component;

@Component
public class EsaOseSmogDataResponseTranslator implements ResponseTranslator<SmogData, GeoData> {

    @Override
    public GeoData translate(SmogData smogData) {
        var data = smogData.getData();
        var schoolData = smogData.getSchool();
        return GeoData.builder()
                .place(getPlace(schoolData))
                .humidity(data.getHumidityAverage())
                .pressure(data.getPressureAverage())
                .temperature(data.getTemperatureAverage())
                .pm10(data.getPm10Average())
                .pm25(data.getPm25Average())
                .timestamp(smogData.getTimestamp())
                .build();
    }

    private Place getPlace(School schoolData) {
        return Place.builder()
                .name(schoolData.getName())
                .city(schoolData.getCity())
                .street(schoolData.getStreet())
                .postalCode(schoolData.getPostalCode())
                .coordinates(getCoordinates(schoolData))
                .build();
    }

    private Coordinates getCoordinates(School schoolData) {
        return Coordinates.builder()
                .longitude(schoolData.getLongitude())
                .latitude(schoolData.getLatitude())
                .build();
    }
}
