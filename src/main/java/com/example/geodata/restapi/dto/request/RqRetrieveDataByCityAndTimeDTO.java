package com.example.geodata.restapi.dto.request;

import java.time.LocalDateTime;

public record RqRetrieveDataByCityAndTimeDTO(String city, LocalDateTime timestamp) {
}
