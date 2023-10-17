package com.example.geodata.repository;

import com.example.geodata.model.GeoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GeoDataRepository extends JpaRepository<GeoData, Long> {

    Optional<GeoData> findFirstByTimestamp(LocalDateTime timestamp);

    @Query("SELECT g FROM GeoData g" +
            " JOIN FETCH g.place" +
            " JOIN FETCH g.place.city" +
            " JOIN FETCH g.place.coordinates" +
            " WHERE g.timestamp > ?1")
    List<GeoData> findAllByTimestampAfter(LocalDateTime timestamp);
}
