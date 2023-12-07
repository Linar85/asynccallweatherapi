//package com.example.asynccallweatherapi.service;
//
//import com.example.asynccallweatherapi.entity.Station;
//import com.example.asynccallweatherapi.repository.StationRepository;
//import okhttp3.mockwebserver.MockResponse;
//import okhttp3.mockwebserver.MockWebServer;
//import okhttp3.mockwebserver.RecordedRequest;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//import reactor.test.StepVerifier;
//
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
//import static org.mockito.ArgumentMatchers.any;
//@ExtendWith(MockitoExtension.class)
//class StationServiceTest {
//    private MockWebServer mockWebServer;
//    @InjectMocks
//    private StationService stationService;
//    @Mock
//    private StationRepository stationRepository;
//    @Value("${auth.headername}")
//    private String HEADER_NAME;
//    @Value("${auth.validapikey}")
//    private String HEADER_VALUE;
//
//
//    @BeforeEach
//    public void setupWebServer() throws InterruptedException, IOException {
//        this.mockWebServer = new MockWebServer();
//        this.mockWebServer.start();
//        this.stationService = new StationService(WebClient.builder(), mockWebServer.url("/").toString());
//
//    }
//
//
//    @Test
//    void getStations() throws InterruptedException {
//        Mockito.when(stationRepository.save(any(Station.class))).thenReturn(Mono.just(new Station()));
//
//        MockResponse mockResponse = new MockResponse()
//                .addHeader("Content-Type", "application/json")
//                .throttleBody(16, 5, TimeUnit.SECONDS);
//        mockWebServer.enqueue(mockResponse);
//
//        StepVerifier.create(stationService.getStations())
//                .verifyComplete();
//
//        RecordedRequest recordedRequest = mockWebServer.takeRequest();
//        Assertions.assertEquals(recordedRequest.getMethod(), "GET");
//        Assertions.assertEquals(recordedRequest.getPath(), "/api/stations");
//
//    }
//}