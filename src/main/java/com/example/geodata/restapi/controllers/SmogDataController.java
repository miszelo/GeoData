package com.example.geodata.restapi.controllers;

import com.example.geodata.restapi.dto.GeoDataDTO;
import com.example.geodata.services.SmogDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.example.geodata.restapi.ApiConst.API;
import static com.example.geodata.restapi.ApiConst.API_VERSION;

@Tag(name = "Smog Data Controller", description = "Smog Data API")
@RestController
@RequestMapping(API + "/" + API_VERSION + "/smog-data")
public class SmogDataController {

    private final SmogDataService smogDataService;

    public SmogDataController(SmogDataService smogDataService) {
        this.smogDataService = smogDataService;
    }

    @Operation(summary = "Get current smog data")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeoDataDTO>> getCurrentGeoData() {
        var geoData = smogDataService.getCurrentGeoData();
        return new ResponseEntity<>(geoData, HttpStatus.OK);
    }


    @Operation(summary = "Get smog data by date")
    @GetMapping(value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeoDataDTO>> getGeoDataByDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        var geoData = smogDataService.getGeoDataByDay(date);
        return ResponseEntity.ok(geoData);
    }

    @Operation(summary = "Get smog data by city and date")
    @GetMapping(value = "/city/{city}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeoDataDTO>> getGeoDataByCityAndDate(
            @PathVariable String city,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        var geoData = smogDataService.getGeoDataByCityAndDate(city, date);
        return ResponseEntity.ok(geoData);
    }

    @Operation(summary = "Get smog data by school name and date")
    @GetMapping(value = "/school/{school}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeoDataDTO>> getGeoDataBySchoolNameAndDate(
            @PathVariable String school,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        var geoData = smogDataService.getGeoDataBySchoolNameAndDate(school, date);
        return ResponseEntity.ok(geoData);
    }

    @Operation(summary = "Save smog data")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeoDataDTO>> saveGeoData() {
        var geoData = smogDataService.saveData();
        return new ResponseEntity<>(geoData, HttpStatus.CREATED);
    }

}
