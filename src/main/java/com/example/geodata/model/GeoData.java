package com.example.geodata.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "geo_data")
public class GeoData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_id", nullable = false)
    private Long dataId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "place_id")
    private Place place;

    private LocalDateTime timestamp;
    @Column(scale = 3)
    private BigDecimal humidity;
    @Column(scale = 3)
    private BigDecimal pressure;
    @Column(scale = 3)
    private BigDecimal temperature;
    @Column(scale = 3)
    private BigDecimal pm10;
    @Column(scale = 3)
    private BigDecimal pm25;
}