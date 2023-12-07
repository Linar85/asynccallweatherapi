package com.example.asynccallweatherapi.repository;

import com.example.asynccallweatherapi.entity.Weather;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface WeatherRepository extends R2dbcRepository<Weather, String> {
    @Query("""                      
            select distinct on (station_code) id, station_code, temp_c, delta_temp, wind_kph, wind_dir, cloud_okt, cloud_type, conditions_text, condition_code, max(record_created_at) as created_at
            from weather
            group by station_code, temp_c, wind_kph, wind_dir, cloud_okt, cloud_type, conditions_text, condition_code, id
              """)
    Flux<Weather> getActualWeathersByLastUpdate();
}
