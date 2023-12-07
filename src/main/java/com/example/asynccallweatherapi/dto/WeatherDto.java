package com.example.asynccallweatherapi.dto;

import com.example.asynccallweatherapi.entity.Station;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherDto {
    private String id;
    private Double tempC;
    private Integer windKph;
    private String windDir;
    private Integer cloudOkt;
    private String cloudType;
    private String conditionsText;
    private Integer conditionCode;
    private String stationCode;
    private LocalDateTime createdAt;
    private Station station;
}