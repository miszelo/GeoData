package com.example.geodata.esaose.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class School {

    private String name;

    private String street;

    @JsonProperty("post_code")
    private String postalCode;

    private String city;

    private Double longitude;

    private Double latitude;

}
