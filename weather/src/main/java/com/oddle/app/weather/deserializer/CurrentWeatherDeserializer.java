package com.oddle.app.weather.deserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oddle.app.weather.dto.entity.json.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CurrentWeatherDeserializer {

	@JsonProperty(value = "coord")
	private Coordinate coordinate;

	@JsonProperty(value = "weather")
	private List<JsonWeather> weather;

	@JsonProperty(value = "base")
	private String base;

	@JsonProperty(value = "main")
	private MainWeather main;

	@JsonProperty(value = "visibility")
	private Long visibility;

	@JsonProperty(value = "wind")
	private Wind wind;

	@JsonProperty(value = "clouds")
	private Cloud clouds;

	@JsonProperty(value = "dt")
	private long date;

	@JsonProperty(value = "timezone")
	private String timezone;

	@JsonProperty(value = "id")
	private Long id;

	@JsonProperty(value = "name")
	private String name;

	@JsonProperty(value = "cod")
	private Long code;


}
