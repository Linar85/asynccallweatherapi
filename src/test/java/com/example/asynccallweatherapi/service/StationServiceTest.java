//package com.example.asynccallweatherapi.service;
//
//import com.example.asynccallweatherapi.entity.Station;
//import com.example.asynccallweatherapi.repository.StationRepository;
//import com.example.asynccallweatherapi.repository.WeatherRepository;
//import okhttp3.mockwebserver.MockResponse;
//import okhttp3.mockwebserver.MockWebServer;
//import org.junit.Ignore;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.test.StepVerifier;
//
//import java.io.IOException;
//class StationServiceTest {
//    private MockWebServer mockWebServer;
//    private StationService stationService;
//    @Mock
//    WebClient webClient;
//    @Mock
//    StationRepository stationRepository;
//    @Mock
//    WeatherRepository weatherRepository;
//
//    @BeforeEach
//    public void setUp() throws IOException {
//        mockWebServer = new MockWebServer();
//        mockWebServer.start();
//
//        WebClient webClient = WebClient.builder().baseUrl(mockWebServer.url("/").toString()).build();
//        stationService = new StationService(webClient, stationRepository, weatherRepository);
//    }
//
//    @AfterEach
//    public void tearDown() throws IOException {
//        mockWebServer.shutdown();
//    }
//
//    @Test
//    public void testGetStations() {
//        Mockito.doNothing().when(webClient.get().header(Mockito.anyString(), Mockito.anyString()));
//
//        Station station1 = new Station();
////        Station station2 = new Station("DEF", "Station 2");
//
//        mockWebServer.enqueue(new MockResponse()
//                .setBody("[{\"stationCode\":\"ABC\",\"stationName\":\"Station 1\"}, {\"stationCode\":\"DEF\",\"stationName\":\"Station 2\"}]"));
//
//
//        // Verifying the result
//        StepVerifier.create(stationService.getStations())
////                .expectNext(station1)
////                .expectNext(station2)
//                .verifyComplete();
//    }
//}
