package com.example.geodata.repository;

import com.example.geodata.model.City;
import com.example.geodata.model.Coordinates;
import com.example.geodata.model.Place;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;


import static com.example.geodata.utils.RepositoryUtils.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlaceRepositoryTest {

    @Autowired PlaceRepository placeRepository;

    @Test
    public void shouldSavePlace() {
        //given
        Coordinates coordinates = getCoordinates(22.0, 25.0);
        City city = getCity("Kraków");
        Place place = getPlace("Szkoła nr1", "Głowna", "00-020", city, coordinates);

        //when
        Place actualPlace = placeRepository.save(place);

        //then
        assertThat(actualPlace.getPlaceId()).isEqualTo(1);
    }

}