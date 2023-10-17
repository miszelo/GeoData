package com.example.geodata.repository;

import com.example.geodata.model.GeoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GeoDataRepository extends JpaRepository<GeoData, Long> {

    Optional<GeoData> findFirstByTimestamp(LocalDateTime timestamp);

    Optional<List<GeoData>> findAllByTimestampAfter(LocalDateTime timestamp);
}
