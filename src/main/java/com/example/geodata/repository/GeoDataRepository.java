package com.example.geodata.repository;

import com.example.geodata.model.GeoData;
import com.example.geodata.restapi.dto.GeoDataByDateRangeDTO;
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

    @Query("SELECT g FROM GeoData g" +
            " JOIN FETCH g.place" +
            " JOIN FETCH g.place.city" +
            " JOIN FETCH g.place.coordinates" +
            " WHERE DATE(g.timestamp) = ?1")
    List<GeoData> findAllByTimestamp(Timestamp timestamp);

    @Query("SELECT g FROM GeoData g" +
            " JOIN FETCH g.place p" +
            " JOIN FETCH g.place.city" +
            " JOIN FETCH g.place.coordinates" +
            " WHERE p.name LIKE ?1" +
            " AND DATE(g.timestamp) = ?2")
    Optional<List<GeoData>> findAllBySchoolNameAndTimestamp(String schoolName, Timestamp timestamp);

    @Query("SELECT g FROM GeoData g" +
            " JOIN FETCH g.place" +
            " JOIN FETCH g.place.city c" +
            " JOIN FETCH g.place.coordinates" +
            " WHERE c.name LIKE ?1" +
            " AND DATE(g.timestamp) = ?2")
    Optional<List<GeoData>> findAllByCityAndTimestamp(String city, Timestamp sqlTimestamp);

    @Query("SELECT NEW com.example.geodata.restapi.dto.GeoDataByDateRangeDTO(" +
            "   p, " +
            "   DATE(g.timestamp), AVG(g.humidity), AVG(g.pressure), AVG(g.temperature), AVG(g.pm10), AVG(g.pm25)" +
            ")" +
            " FROM GeoData g" +
            " JOIN g.place p" +
            " JOIN p.city c" +
            " WHERE p.name LIKE ?1" +
            " AND g.timestamp BETWEEN ?2 AND ?3" +
            " GROUP BY DATE(g.timestamp), p.placeId")
    Optional<List<GeoDataByDateRangeDTO>> findAverageBySchoolNameAndTimestampBetween(String schoolName, LocalDateTime startDateTime, LocalDateTime endDateTime);


    @Query("SELECT NEW com.example.geodata.restapi.dto.GeoDataByDateRangeDTO(" +
            "   p, " +
            "   DATE(g.timestamp), AVG(g.humidity), AVG(g.pressure), AVG(g.temperature), AVG(g.pm10), AVG(g.pm25)" +
            ")" +
            " FROM GeoData g" +
            " JOIN g.place p" +
            " JOIN p.city c" +
            " WHERE c.name LIKE ?1" +
            " AND g.timestamp BETWEEN ?2 AND ?3" +
            " GROUP BY DATE(g.timestamp), p.placeId")
    Optional<List<GeoDataByDateRangeDTO>> findAverageByCityAndTimestampBetween(String city, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
