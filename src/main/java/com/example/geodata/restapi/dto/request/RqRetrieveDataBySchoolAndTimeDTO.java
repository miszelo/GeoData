package com.example.geodata.restapi.dto.request;

import java.time.LocalDateTime;

public record RqRetrieveDataBySchoolAndTimeDTO(String schoolName, LocalDateTime timestamp) {
}
