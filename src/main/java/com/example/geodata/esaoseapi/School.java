package com.example.geodata.esaoseapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class School {

    private String name;

    private String street;

    private String post_code;

    private String city;

    private Double longitude;

    private Double latitude;

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", post_code='" +  post_code + '\'' +
                ", city='" + city + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
