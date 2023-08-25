package com.example.geodata.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@Table(name = "coordinates")
@AllArgsConstructor
public class Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coordinates_id", nullable = false)
    private Long coordinatesId;

    @OneToOne(mappedBy = "coordinates")
    private Place place;

    private double longitude;
    private double latitude;
}
