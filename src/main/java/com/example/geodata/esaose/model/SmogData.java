package com.example.geodata.esaose.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SmogData {

    private School school;

    private Data data;

    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
