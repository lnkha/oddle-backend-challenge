package com.oddle.app.weather.dto.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherChangeRequest {

	private Long id;

	private String main;

	private String description;

	private double temperature;

	private double windSpeed;

	private double clouds;

}
