package com.example.geodata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Place place;

    private LocalDateTime timestamp;
    @Column(scale = 4)
    private BigDecimal humidity;
    @Column(scale = 4)
    private BigDecimal pressure;
    @Column(scale = 4)
    private BigDecimal temperature;
    @Column(scale = 4)
    private BigDecimal pm10;
    @Column(scale = 2)
    private BigDecimal pm25;
}