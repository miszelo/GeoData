package com.example.geodata.repository;

import com.example.geodata.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Override
    @NonNull
    @Query("SELECT p FROM Place p" +
            " JOIN FETCH p.city" +
            " JOIN FETCH p.coordinates")
    List<Place> findAll();
}
