package com.example.geodata.esaoseapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFromApi {

    private List<SmogData> smog_data;
}
