package com.example.geodata.restapi.controllers;

import com.example.geodata.services.SmogDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.geodata.restapi.ApiConst.API;
import static com.example.geodata.restapi.ApiConst.API_VERSION;

@RestController
@RequestMapping(API + "/" + API_VERSION)
public class SmogDataController {

    private final SmogDataService smogDataService;

    public SmogDataController(SmogDataService smogDataService) {
        this.smogDataService = smogDataService;
    }

    @PostMapping(value = "/smog-data")
    public ResponseEntity<?> saveDataToDatabase() {
        var geoData = smogDataService.saveData();
        return new ResponseEntity<>(geoData, HttpStatus.CREATED);
    }

    @GetMapping(value = "/smog-data")
    public ResponseEntity<?> getCurrentDataFromDatabase() {
        var geoData = smogDataService.getGeoData();
        return new ResponseEntity<>(geoData, HttpStatus.OK);
    }
}
