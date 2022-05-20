package com.oddle.app.weather.repository;

import com.oddle.app.weather.dto.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

	Optional<Weather> findByNameAndDateBetween(String name, long from, long to);

}
