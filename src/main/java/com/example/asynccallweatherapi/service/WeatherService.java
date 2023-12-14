package com.example.asynccallweatherapi.service;

import com.example.asynccallweatherapi.entity.Weather;
import com.example.asynccallweatherapi.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Comparator;


@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final WeatherRepository weatherRepository;

    @Scheduled(initialDelay = 2000, fixedRate = 60 * 1000)
    public Mono<Void> maxTempProcessing() {
        return weatherRepository.getActualWeathersByLastUpdate()
                .sort(Comparator.comparing(Weather::getTempC).reversed().thenComparing(Weather::getStationCode))
                .take(5)
                .doOnNext(weather -> log.info("max TEMPERATURE: " + weather))
                .then();
    }

    @Scheduled(initialDelay = 4000, fixedRate = 60 * 1000)
    public Mono<Void> maxDeltaProcessing() {
        return weatherRepository.getActualWeathersByLastUpdate()
                .sort(Comparator.comparing(Weather::getDeltaTemp).reversed())
                .take(5)
                .doOnNext(weather -> log.info("max DELTA: " + weather))
                .then();
    }
}