package com.example.asynccallweatherapi.repository;

import com.example.asynccallweatherapi.entity.Station;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends R2dbcRepository<Station, String> {
}
