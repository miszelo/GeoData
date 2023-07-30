package com.example.geodata.translators;

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
        return GeoData.builder()
                .place(getPlace(smogData))
                .humidity(data.getHumidityAverage())
                .pressure(data.getPressureAverage())
                .temperature(data.getTemperatureAverage())
                .pm10(data.getPm10Average())
                .pm25(data.getPm25Average())
                .timestamp(smogData.getTimestamp())
                .build();
    }

    private Place getPlace(SmogData smogData) {
        return Place.builder()
                .name(smogData.getSchool().getName())
                .city(smogData.getSchool().getCity())
                .street(smogData.getSchool().getStreet())
                .postalCode(smogData.getSchool().getPostalCode())
                .coordinates(Coordinates.builder()
                        .longitude(smogData.getSchool().getLongitude())
                        .latitude(smogData.getSchool().getLatitude())
                        .build())
                .build();
    }
}
