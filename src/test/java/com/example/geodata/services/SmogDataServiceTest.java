package com.example.geodata.services;

import com.example.geodata.esaose.EsaOseDataRetriever;
import com.example.geodata.esaose.model.Data;
import com.example.geodata.esaose.model.EsaOseData;
import com.example.geodata.esaose.model.School;
import com.example.geodata.esaose.model.SmogData;
import com.example.geodata.model.City;
import com.example.geodata.model.Coordinates;
import com.example.geodata.model.GeoData;
import com.example.geodata.model.Place;
import com.example.geodata.repository.GeoDataRepository;
import com.example.geodata.repository.PlaceRepository;
import com.example.geodata.translators.EsaOseSmogDataResponseTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.geodata.utils.RepositoryUtils.*;
import static com.example.geodata.utils.ServiceUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SmogDataServiceTest {

    private SmogDataService smogDataService;
    private EsaOseSmogDataResponseTranslator esaOseSmogDataResponseTranslator;
    private EsaOseDataRetriever esaOseDataRetriever;
    private GeoDataRepository geoDataRepository;
    private PlaceRepository placeRepository;

    @BeforeEach
    void setUp() {
        esaOseSmogDataResponseTranslator = mock(EsaOseSmogDataResponseTranslator.class);
        esaOseDataRetriever = mock(EsaOseDataRetriever.class);
        geoDataRepository = mock(GeoDataRepository.class);
        placeRepository = mock(PlaceRepository.class);
        smogDataService = new SmogDataService(esaOseSmogDataResponseTranslator, esaOseDataRetriever, geoDataRepository, placeRepository);
    }

    @Test
    void shouldReturnSmogData() {
        //given
        Data data1 = getData(10.0, 20.0, 30.0, 40.0, 50.0);
        School school1 = getSchool("city1", "name1", "street1", "postalCode1", 1.0, 2.0);
        LocalDateTime timestamp = getTimestamp("2023-01-01T12:00:00");
        SmogData smogData1 = getSmogData(data1, school1, timestamp);
        Data data2 = getData(1.0, 2.0, 3.0, 4.0, 5.0);
        School school2 = getSchool("city2", "name2", "street2", "postalCode2", 12.0, 22.0);
        SmogData smogData2 = getSmogData(data2, school2, timestamp);
        List<SmogData> smogDataList = List.of(smogData1, smogData2);
        EsaOseData esaOseData = getEsaOseData(smogDataList);
        City city1 = getCity("city1");
        Coordinates coordinates1 = getCoordinates(1.0, 2.0);
        Place place1 = getPlace("name1", "street1", "postalCode1", city1, coordinates1);
        City city2 = getCity("city2");
        Coordinates coordinates2 = getCoordinates(12.0, 22.0);
        Place place2 = getPlace("name2", "street2", "postalCode2", city2, coordinates2);
        List<GeoData> geoDataList = List.of(
                getGeoData(place1, timestamp, BigDecimal.valueOf(10), BigDecimal.valueOf(20), BigDecimal.valueOf(30), BigDecimal.valueOf(40), BigDecimal.valueOf(50)),
                getGeoData(place2, timestamp, BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5))
        );

        //when
        when(esaOseDataRetriever.getEsaOseData()).thenReturn(esaOseData);
        when(geoDataRepository.findFirstByTimestamp(any())).thenReturn(Optional.empty());
        when(esaOseSmogDataResponseTranslator.translate(smogData1)).thenReturn(geoDataList.get(0));
        when(esaOseSmogDataResponseTranslator.translate(smogData2)).thenReturn(geoDataList.get(1));
        when(placeRepository.findAll()).thenReturn(List.of());

        var savedGeoData = smogDataService.saveData();


        //then
        assertThat(savedGeoData).hasSize(2);
    }

}