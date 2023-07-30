package com.example.geodata.esaose.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EsaOseData {

    @JsonProperty("smog_data")
    private List<SmogData> smogDataList;
}
