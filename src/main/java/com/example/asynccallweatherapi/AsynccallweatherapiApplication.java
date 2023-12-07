package com.example.asynccallweatherapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AsynccallweatherapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsynccallweatherapiApplication.class, args);
    }
}