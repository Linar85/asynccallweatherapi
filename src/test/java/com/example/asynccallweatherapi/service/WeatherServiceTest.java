package com.example.asynccallweatherapi.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.example.asynccallweatherapi.entity.Weather;
import com.example.asynccallweatherapi.repository.WeatherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {
    @InjectMocks
    WeatherService weatherService;
    @Mock
    WeatherRepository weatherRepository;
    private static MemoryAppender memoryAppender;
    private static final String LOGGER_NAME = "com.example.asynccallweatherapi.service.WeatherService";
    private static final String MSG_MAX = "max TEMPERATURE: ";
    private static final String MSG_DELTA = "max DELTA: ";

    List<Weather> weathers = new CopyOnWriteArrayList<>();

    @BeforeEach
    public void initData() {

        weathers.clear();
        for (int i = 0; i < 10; i++) {
            weathers.add(Weather.builder()
                    .id("id" + i)
                    .stationCode("station" + i)
                    .tempC(i)
                    .deltaTemp(2 * i)
                    .build());
        }

        Logger logger = (Logger) LoggerFactory.getLogger(LOGGER_NAME);
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.DEBUG);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @AfterEach
    public void close(){
        memoryAppender.stop();
    }

    @Test
    void maxTempProcessing() {

        Mockito.when(weatherRepository.getActualWeathersByLastUpdate()).thenReturn(Flux.fromIterable(weathers));
        StepVerifier.create(weatherService.maxTempProcessing())
                .verifyComplete();

        assertEquals(memoryAppender.countEventsForLogger(LOGGER_NAME), 5);
        assertEquals(memoryAppender.search(MSG_MAX + weathers.get(9).toString(), Level.INFO).size(), 1);
        assertNotEquals(memoryAppender.search(MSG_MAX + weathers.get(1).toString(), Level.INFO).size(), 1);
    }

    @Test
    void maxDeltaProcessing() {

        Mockito.when(weatherRepository.getActualWeathersByLastUpdate()).thenReturn(Flux.fromIterable(weathers));
        StepVerifier.create(weatherService.maxDeltaProcessing())
                .verifyComplete();

        assertEquals(memoryAppender.countEventsForLogger(LOGGER_NAME), 5);
        assertEquals(memoryAppender.search(MSG_DELTA + weathers.get(9).toString(), Level.INFO).size(), 1);
        assertNotEquals(memoryAppender.search(MSG_DELTA + weathers.get(1).toString(), Level.INFO).size(), 1);
    }
}


