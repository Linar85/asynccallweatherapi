package com.example.asynccallweatherapi.service;

import com.example.asynccallweatherapi.entity.Station;
import com.example.asynccallweatherapi.repository.StationRepository;
import com.example.asynccallweatherapi.repository.WeatherRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class StationService {

    @Autowired
    private final WebClient webClient;
    private final StationRepository stationRepository;
    private final WeatherRepository weatherRepository;

//    @Qualifier("dbClient")
//    private final DatabaseClient databaseClient;
//    private final PostgresqlConnectionFactory connectionFactory;

//    @Value("${util.batch.size}")
//    private Integer BATCH_SIZE;
    public ArrayBlockingQueue<Station> arrayBlockingQueue = new ArrayBlockingQueue<>(1000);
    public Map<String, Integer> deltaMap = new ConcurrentHashMap<>();

    @Value("${auth.headername}")
    private String HEADER_NAME;
    @Value("${auth.validapikey}")
    private String HEADER_VALUE;


    @EventListener(ApplicationReadyEvent.class)
    public Mono<Void> getStations() {
        log.info("GET method running...");
        return webClient.get()
                .uri("/stations")
                .header(HEADER_NAME, HEADER_VALUE)
                .retrieve()
                .bodyToFlux(Station.class)
                .flatMap(stationRepository::save)
                .doOnNext(station -> arrayBlockingQueue.add(station))
                .doOnNext(station -> deltaMap.put(station.getStationCode(), 0))
                .then();
    }

    // Рабочий вариант без батчей
    @Scheduled(initialDelay = 5000, fixedRate = 1 * 60 * 1000)
    public Mono<Void> getWeathers() {
        log.info("scheduled method started");
        return Flux.fromIterable(arrayBlockingQueue)
                .flatMap(station -> webClient.get()
                        .uri("/stations/" + station.getStationCode())
                        .header(HEADER_NAME, HEADER_VALUE)
                        .retrieve()
                        .bodyToMono(Station.class)
                        .publishOn(Schedulers.boundedElastic())
                        .map(st -> {
                            st.getWeather().setDeltaTemp(Math.abs(st.getWeather().getTempC() - deltaMap.get(st.getStationCode())));
                            weatherRepository.save(st.getWeather()).subscribe();
                            deltaMap.put(st.getWeather().getStationCode(), st.getWeather().getTempC());
                            return st;
                        })
                )
                .then();
    }

    //  Попытка добавить батчи
//    @Scheduled(initialDelay = 5000, fixedRate = 30000)
//    public Mono<Void> getWeathers() {
//        log.info("scheduled method started");
//        return Flux.fromIterable(arrayBlockingQueue)
//                .flatMap(station -> webClient.get()
//                        .uri("/stations/" + station.getStationCode())
//                        .header(HEADER_NAME, HEADER_VALUE)
//                        .retrieve()
//                        .bodyToMono(Station.class)
//                        .map(st -> {
//                            Mono.from(connectionFactory.create())
//                                    .map(connection -> {
//                                        val batch = connection.createBatch();
//
//                                            .add("""
//                                                 INSERT INTO callweatherapi.public.weather (temp_c, delta_temp, wind_kph, wind_dir, cloud_okt, cloud_type, conditions_text, condition_code, station_code, created_at)
//                                                 VALUES (:tempC, :deltaTemp,:windKph, :windDir, :cloudOkt, :cloudType, :conditionsText, :conditionCode, :stationCode, :createdAt)
//                                                """
//                                        )
//
//                                                .execute();
//
//                                        st.getWeather().setDeltaTemp(Math.abs(st.getWeather().getTempC() - deltaMap.get(st.getStationCode())));
//                                        weatherRepository.save(st.getWeather()).subscribe();
//                                        deltaMap.put(st.getWeather().getStationCode(), st.getWeather().getTempC());
//                                        return st;
//                                    })
//                )
//                .then();
//                        }
}
