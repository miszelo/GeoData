package com.example.geodata.repository;

import com.example.geodata.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findFirstByNameAndCityAndStreetAndPostalCode(String placeName, String city, String street, String postalCode);
}
