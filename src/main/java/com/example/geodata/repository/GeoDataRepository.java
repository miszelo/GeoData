package com.example.geodata.repository;

import com.example.geodata.model.GeoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GeoDataRepository extends JpaRepository<GeoData, Long> {

    Optional<GeoData> findFirstByTimestamp(LocalDateTime timestamp);

    void deleteAllByTimestamp(LocalDateTime timestamp);

    @Query("SELECT g FROM GeoData g" +
            " JOIN FETCH g.place" +
            " JOIN FETCH g.place.city" +
            " JOIN FETCH g.place.coordinates" +
            " WHERE g.timestamp > ?1")
    Optional<List<GeoData>> findAllByTimestampAfter(LocalDateTime timestamp);


    @Query("SELECT g FROM GeoData g" +
            " JOIN FETCH g.place" +
            " JOIN FETCH g.place.city" +
            " JOIN FETCH g.place.coordinates" +
            " WHERE DATE(g.timestamp) = ?1")
    List<GeoData> findAllByTimestamp(Timestamp timestamp);


    @Query("SELECT g FROM GeoData g" +
            " JOIN FETCH g.place" +
            " JOIN FETCH g.place.city" +
            " JOIN FETCH g.place.coordinates" +
            " WHERE g.place.city.name = ?1" +
            " AND g.timestamp > ?2")
    Optional<List<GeoData>> findAllBySchoolNameAndTimestamp(String schoolName, LocalDateTime timestamp);
}
