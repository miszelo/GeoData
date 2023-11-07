package com.example.geodata.repository;

import com.example.geodata.model.City;
import com.example.geodata.model.Coordinates;
import com.example.geodata.model.GeoData;
import com.example.geodata.model.Place;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.geodata.utils.RepositoryUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GeoDataRepositoryTest {

    @Autowired
    private GeoDataRepository geoDataRepository;

    @Test
    public void shouldSaveGeoData() {
        //given
        Coordinates coordinates = getCoordinates(50.0, 25.0);
        City city = getCity("Warszawa");
        Place place = getPlace("Place1", "Street1", "00-000", city, coordinates);

        List<GeoData> geoDataList = List.of(
                getGeoData(place,
                        LocalDateTime.parse("2023-12-11T21:37:00"),
                        BigDecimal.valueOf(10),
                        BigDecimal.valueOf(20),
                        BigDecimal.valueOf(30),
                        BigDecimal.valueOf(40),
                        BigDecimal.valueOf(50))
        );

        //when
        var savedGeoData = geoDataRepository.saveAll(geoDataList);

        //then
        assertThat(savedGeoData).hasSize(1);
    }

}