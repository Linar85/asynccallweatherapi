//package com.example.asynccallweatherapi.repository;
//
//import com.example.asynccallweatherapi.entity.Weather;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.lifecycle.Startables;
//import reactor.test.StepVerifier;
//
//import java.time.LocalDateTime;
//
//import static java.lang.String.format;
//
//@DataR2dbcTest
//@ExtendWith(SpringExtension.class)
//@Testcontainers
//class WeatherRepositoryTest {
//
//    @Autowired
//    WeatherRepository weatherRepository;
//
//    @Container
//    public static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16")
//            .withDatabaseName("callweatherapi")
//            .withUsername("postgres")
//            .withPassword("1234")
//            .withInitScript("sql/WeatherReposotory.sql");
//
//    @BeforeAll
//    static void runContainer() {
//        Startables.deepStart(postgreSQLContainer);
//    }
//    @AfterAll
//    static void stopContainer(){
//        postgreSQLContainer.stop();
//    }
//
//    @DynamicPropertySource
//    private static void setDatasourceProperties(DynamicPropertyRegistry registry) {
//
//        registry.add("spring.r2dbc.url", () ->
//                format("r2dbc:pool:postgresql://%s:%d/%s",
//                        postgreSQLContainer.getHost(),
//                        postgreSQLContainer.getFirstMappedPort(),
//                        postgreSQLContainer.getDatabaseName()));
//        registry.add("spring.r2dbc.username", postgreSQLContainer::getUsername);
//        registry.add("spring.r2dbc.password", postgreSQLContainer::getPassword);
//    }
//    Weather weather1 = Weather.builder()
//            .stationCode("TEST1")
//            .recordCreatedAt(LocalDateTime.now())
//            .build();
//    Weather weather2 = Weather.builder()
//            .stationCode("TEST2")
//            .recordCreatedAt(LocalDateTime.now())
//            .build();
//    Weather weather3 = Weather.builder()
//            .stationCode("TEST3")
//            .recordCreatedAt(LocalDateTime.now())
//            .build();
//
//    @Test
//    void findAllByStationCodeSingle() {
//        StepVerifier.create(weatherRepository.save(weather2)
//                        .then(weatherRepository.save(weather1))
//                        .then(weatherRepository.save(weather3))
//                        .doOnNext(w -> weatherRepository.getActualWeathersByLastUpdate()))
//                .expectNext(weather3)
//                .verifyComplete();
//    }
//
//    @Test
//    void finalCount() {
//        StepVerifier.create(weatherRepository.getActualWeathersByLastUpdate())
//                .expectNextCount(500L)
//                .verifyComplete();
//    }
//}