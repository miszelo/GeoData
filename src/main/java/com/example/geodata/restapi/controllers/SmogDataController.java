package com.example.geodata.restapi.controllers;

import com.example.geodata.restapi.dto.*;
import com.example.geodata.restapi.dto.request.RqDeleteDataByTimeDTO;
import com.example.geodata.restapi.dto.request.RqRetrieveDataByCityAndTimeDTO;
import com.example.geodata.restapi.dto.request.RqRetrieveDataByLocalDateTimeDTO;
import com.example.geodata.restapi.dto.request.RqRetrieveDataBySchoolAndTimeDTO;
import com.example.geodata.services.SmogDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.geodata.restapi.ApiConst.API;
import static com.example.geodata.restapi.ApiConst.API_VERSION;

@Tag(name = "Smog Data Controller", description = "Smog Data API")
@RestController
@RequestMapping(API + "/" + API_VERSION)
public class SmogDataController {

    private final SmogDataService smogDataService;

    public SmogDataController(SmogDataService smogDataService) {
        this.smogDataService = smogDataService;
    }

    @Operation(summary = "Get current smog data")
    @GetMapping(value = "/smog-data",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeoDataDTO>> getCurrentGeoData() {
        var geoData = smogDataService.getCurrentGeoData();
        return new ResponseEntity<>(geoData, HttpStatus.OK);
    }


    @Operation(summary = "Get smog data by timestamp")
    @GetMapping(value = "/smog-data/day",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeoDataDTO>> getGeoDataByDay(@RequestBody RqRetrieveDataByLocalDateTimeDTO rq) {
        var geoData = smogDataService.getGeoDataByTimestamp(rq.timestamp());
        return ResponseEntity.ok(geoData);
    }

    @Operation(summary = "Get smog data by city and timestamp")
    @GetMapping(value = "/smog-data/city",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeoDataDTO>> getGeoDataByCityAndTimestamp(@RequestBody RqRetrieveDataByCityAndTimeDTO rq) {
        var geoData = smogDataService.getGeoDataByCityAndTimeStamp(rq.city(), rq.timestamp());
        return ResponseEntity.ok(geoData);
    }

    @Operation(summary = "Get smog data by school name and timestamp")
    @GetMapping(value = "/smog-data/school",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeoDataDTO>> getGeoDataBySchoolNameAndTimestamp(@RequestBody RqRetrieveDataBySchoolAndTimeDTO rq) {
        var geoData = smogDataService.getGeoDataBySchoolNameAndTimestamp(rq.schoolName(), rq.timestamp());
        return ResponseEntity.ok(geoData);
    }

    @Operation(summary = "Save smog data")
    @PostMapping(value = "/smog-data",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeoDataDTO>> saveGeoData() {
        var geoData = smogDataService.saveData();
        return new ResponseEntity<>(geoData, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete smog data by timestamp")
    @DeleteMapping(value = "/smog-data")
    public ResponseEntity<Void> deleteGeoData(@RequestBody RqDeleteDataByTimeDTO rq) {
        smogDataService.deleteData(rq.timestamp());
        return ResponseEntity.noContent().build();
    }

}
