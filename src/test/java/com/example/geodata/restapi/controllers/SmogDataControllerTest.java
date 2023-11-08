package com.example.geodata.restapi.controllers;

import com.example.geodata.restapi.dto.CoordinatesDTO;
import com.example.geodata.restapi.dto.GeoDataDTO;
import com.example.geodata.restapi.dto.PlaceDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static com.example.geodata.restapi.controllers.DtoUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SmogDataController.class)
public class SmogDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnSmogData() throws Exception {
        //given
        String city = "Warszawa";
        CoordinatesDTO coordinates = getCoordinatesDto(50.0, 25.0);
        PlaceDTO place = getPlaceDto("Place1", "Street1", "00-000", city, coordinates);
        List<GeoDataDTO> geoDataList = List.of(
                getGeoDataDto(place,
                        "2023-12-11T21:37:00",
                        BigDecimal.valueOf(10),
                        BigDecimal.valueOf(20),
                        BigDecimal.valueOf(30),
                        BigDecimal.valueOf(40),
                        BigDecimal.valueOf(50))
        );

        //when
        //then
        this.mockMvc.perform(post("/api/v1/smog-data")
                        .content(objectMapper.writeValueAsString(geoDataList)))
                .andExpect(status().isCreated());

    }
}