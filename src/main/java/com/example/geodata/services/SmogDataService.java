package com.example.geodata.services;

import com.example.geodata.esaose.EsaOseDataRetriever;
import com.example.geodata.esaose.model.EsaOseData;
import com.example.geodata.model.Coordinates;
import com.example.geodata.model.GeoData;
import com.example.geodata.model.Place;
import com.example.geodata.repository.CityRepository;
import com.example.geodata.repository.GeoDataRepository;
import com.example.geodata.repository.PlaceRepository;
import com.example.geodata.restapi.dto.GeoDataByDateRangeDTO;
import com.example.geodata.restapi.dto.GeoDataDTO;
import com.example.geodata.translators.EsaOseSmogDataResponseTranslator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static com.example.geodata.restapi.mappers.GeoDataMapper.mapGeoDataToGeoDataDTO;

@Service
@EnableScheduling
public class SmogDataService {
    private final EsaOseSmogDataResponseTranslator esaOseSmogDataResponseTranslator;
    private final EsaOseDataRetriever esaOseDataRetriever;
    private final GeoDataRepository geoDataRepository;
    private final PlaceRepository placeRepository;
    private final CityRepository cityRepository;

    public SmogDataService(EsaOseSmogDataResponseTranslator esaOseSmogDataResponseTranslator,
                           EsaOseDataRetriever esaOseDataRetriever,
                           GeoDataRepository geoDataRepository,
                           PlaceRepository placeRepository,
                           CityRepository cityRepository) {
        this.esaOseSmogDataResponseTranslator = esaOseSmogDataResponseTranslator;
        this.esaOseDataRetriever = esaOseDataRetriever;
        this.geoDataRepository = geoDataRepository;
        this.placeRepository = placeRepository;
        this.cityRepository = cityRepository;
    }

    @Transactional
    @Scheduled(cron = "0 0 */4 * * *", zone = "Europe/Paris")
    public void saveData() {
        var esaOseData = esaOseDataRetriever.getEsaOseData();
        var geoDataList = getGeoDataList(esaOseData);

        if (geoDataRepository.findFirstByTimestamp(geoDataList.get(0).getTimestamp()).isPresent()) {
            return;
        }

        for (var geoData : geoDataList) {
            var place = geoData.getPlace();
            var existingPlace = placeRepository.findByName(place.getName());
            var city = place.getCity();
            var existingCity = cityRepository.findByName(city.getName());
            existingPlace.ifPresent(geoData::setPlace);
            existingCity.ifPresent(place::setCity);
            geoDataRepository.save(geoData);
        }
    }

    @Cacheable("currentGeoData")
    public List<GeoDataDTO> getCurrentGeoData() {
        var esaOseData = esaOseDataRetriever.getEsaOseData();
        var geoDataList = getGeoDataList(esaOseData);
        return mapGeoDataToGeoDataDTO(geoDataList);
    }

    @Cacheable("geoDataByDay")
    public List<GeoDataDTO> getGeoDataByDay(LocalDate localDate) {
        Timestamp sqlTimestamp = Timestamp.valueOf(localDate.atStartOfDay());
        var geoData = geoDataRepository.findAllByTimestamp(sqlTimestamp);
        return mapGeoDataToGeoDataDTO(geoData);
    }

    @Cacheable("geoDataBySchoolNameAndDate")
    public List<GeoDataDTO> getGeoDataBySchoolNameAndDate(String schoolName, LocalDate localDate) {
        Timestamp sqlTimestamp = Timestamp.valueOf(localDate.atStartOfDay());
        var geoData = geoDataRepository.findAllBySchoolNameAndTimestamp(schoolName, sqlTimestamp)
                .orElseThrow();
        return mapGeoDataToGeoDataDTO(geoData);
    }

    @Cacheable("geoDataByCityAndDate")
    public List<GeoDataDTO> getGeoDataByCityAndDate(String city, LocalDate localDate) {
        Timestamp sqlTimestamp = Timestamp.valueOf(localDate.atStartOfDay());
        var geoData = geoDataRepository.findAllByCityAndTimestamp(city, sqlTimestamp)
                .orElseThrow();
        return mapGeoDataToGeoDataDTO(geoData);
    }

    @Cacheable("geoDataByCityAndDateRange")
    public List<GeoDataByDateRangeDTO> getGeoDataByCityAndDateRange(String city, LocalDateTime startDate, LocalDateTime endDate) {
        return geoDataRepository.findAverageByCityAndTimestampBetween(city, startDate, endDate)
                .orElseThrow();
    }

    @Cacheable("geoDataBySchoolNameAndDateRange")
    public List<GeoDataByDateRangeDTO> getGeoDataBySchoolNameAndDateRange(String schoolName, LocalDateTime startDate, LocalDateTime endDate) {
        return geoDataRepository.findAverageBySchoolNameAndTimestampBetween(schoolName, startDate, endDate)
                .orElseThrow();
    }
    private List<GeoData> getGeoDataList(EsaOseData esaOseData) {
        var coordinates = new HashSet<Coordinates>();
        var places = new HashSet<Place>();
        return esaOseData.getSmogDataList().stream()
                .map(esaOseSmogDataResponseTranslator::translate)
                .filter(filterRepeatedPlaces(places))
                .filter(filterRepeatedCoordinates(coordinates))
                .toList();
    }

    private Predicate<GeoData> filterRepeatedCoordinates(Set<Coordinates> geoDataSet) {
        return smogData -> geoDataSet.add(smogData.getPlace().getCoordinates());
    }

    private Predicate<GeoData> filterRepeatedPlaces(Set<Place> places) {
        return smogData -> places.add(smogData.getPlace());
    }

}
