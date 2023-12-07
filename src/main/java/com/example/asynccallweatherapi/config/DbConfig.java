//package com.example.asynccallweatherapi.config;
//
//import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
//import io.r2dbc.postgresql.PostgresqlConnectionFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
//import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
//import org.springframework.r2dbc.core.DatabaseClient;
//
///**
// * конфиг для батчей
// */
//
//@Configuration
//@EnableR2dbcRepositories(basePackages = "com.example.asynccallweatherapi.repository")
//public class DbConfig extends AbstractR2dbcConfiguration {
//
//    @Value("${spring.r2dbc.url}")
//    private String URL;
//    @Value("${spring.r2dbc.username}")
//    private String USERNAME;
//    @Value("${spring.r2dbc.password}")
//    private String PASSWORD;
//
//    @Override
//    @Bean
//    public PostgresqlConnectionFactory connectionFactory() {
//        return new PostgresqlConnectionFactory(
//                PostgresqlConnectionConfiguration.builder()
//                        .host(URL)
//                        .username(USERNAME)
//                        .password(PASSWORD)
//                        .build());
//    }
//
//    @Bean
//    public DatabaseClient dbClient(PostgresqlConnectionFactory connectionFactory) {
//        return DatabaseClient.create(connectionFactory)
//                .sql("""
//                          INSERT INTO callweatherapi.public.weather (temp_c, delta_temp, wind_kph, wind_dir, cloud_okt, cloud_type, conditions_text, condition_code, station_code, created_at)
//                          VALUES (:tempC, :deltaTemp,:windKph, :windDir, :cloudOkt, :cloudType, :conditionsText, :conditionCode, :stationCode, :createdAt)
//                        """)
//                .bind("id", )
//    }
//}