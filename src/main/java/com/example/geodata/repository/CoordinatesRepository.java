package com.example.geodata.repository;

import com.example.geodata.model.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
    Optional<Coordinates> findFirstByLongitudeAndLatitude(double longitude, double latitude);
}
