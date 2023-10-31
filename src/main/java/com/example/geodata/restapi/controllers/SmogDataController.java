package com.example.geodata.restapi.controllers;

import com.example.geodata.restapi.dto.GeoDataDTO;
import com.example.geodata.restapi.dto.RequestRetrieveDataByLocalDateDto;
import com.example.geodata.services.SmogDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.geodata.restapi.ApiConst.API;
import static com.example.geodata.restapi.ApiConst.API_VERSION;

@RestController
@RequestMapping(API + "/" + API_VERSION)
public class SmogDataController {

    private final SmogDataService smogDataService;

    public SmogDataController(SmogDataService smogDataService) {
        this.smogDataService = smogDataService;
    }

    @PostMapping(value = "/smog-data",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeoDataDTO>> saveGeoData() {
        var geoData = smogDataService.saveData();
        return new ResponseEntity<>(geoData, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/smog-data",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteGeoData(@RequestParam LocalDateTime localDateTime) {
        smogDataService.deleteData(localDateTime);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping(value = "/smog-data/{city}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<GeoDataDTO>> getGeoDataByCity(@PathVariable String city) {
//        var geoData = smogDataService.getGeoDataByCity(city);
//        return new ResponseEntity<>(geoData, HttpStatus.OK);
//    }
//
//    @GetMapping(value = "/smog-data/{schoolName}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<GeoDataDTO>> getGeoDataBySchoolName(@PathVariable String schoolName) {
//        var geoData = smogDataService.getGeoDataBySchoolName(schoolName);
//        return new ResponseEntity<>(geoData, HttpStatus.OK);
//    }
//
//    @GetMapping(value = "/smog-data/{schoolName}/{timestamp}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<GeoDataDTO>> getGeoDataBySchoolNameAndTimestamp(
//            @PathVariable String schoolName,
//            @PathVariable LocalDateTime timestamp) {
//        var geoData = smogDataService.getGeoDataBySchoolNameAndTimestamp(schoolName, timestamp);
//        return new ResponseEntity<>(geoData, HttpStatus.OK);
//}

    @GetMapping(value = "/smog-data",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGeoDataByDay(@RequestBody RequestRetrieveDataByLocalDateDto timestamp) {
        var geoData = smogDataService.getGeoDataByTimestamp(timestamp);
        return new ResponseEntity<>(geoData, HttpStatus.OK);

    }

    @GetMapping(value = "/smog-data",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeoDataDTO>> getGeoData() {
        var geoData = smogDataService.getCurrentGeoData();
        return new ResponseEntity<>(geoData, HttpStatus.OK);
    }
}
