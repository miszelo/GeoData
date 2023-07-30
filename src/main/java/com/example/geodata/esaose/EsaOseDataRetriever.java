package com.example.geodata.esaose;

import com.example.geodata.esaose.model.EsaOseData;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static com.example.geodata.esaose.EsaOseConst.ESA_OSE_URL;

@Component
public class EsaOseDataRetriever {
    private final RestTemplate restTemplate;

    public EsaOseDataRetriever(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EsaOseData getEsaOseData() {
        var response = restTemplate.getForEntity(URI.create(ESA_OSE_URL), EsaOseData.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("TODO");
        }
        return response.getBody();

    }
}
