package com.oddle.app.weather.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class WeatherResponse {

	private Long id;

	private String name;

	private String main;

	private String description;

	private double temperature;

	private double wind;

	private double clouds;

	private long date;
}
