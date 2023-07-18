package com.example.geodata;

import com.example.geodata.esaoseapi.ResponseFromApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@SpringBootApplication
public class GeoDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeoDataApplication.class, args);

        String url = "https://public-esa.ose.gov.pl/api/v1/smog";

        RestTemplate restTemplate = new RestTemplate();
        var a = restTemplate.getForObject(URI.create(url), ResponseFromApi.class);
    }

}
