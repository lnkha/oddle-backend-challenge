package com.oddle.app.weather.dto.entity.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MainWeather {

	@JsonProperty(value = "temp")
	private double temp;

	@JsonProperty(value = "feels_like")
	private double feelsLike;

	@JsonProperty(value = "temp_min")
	private double tempMin;

	@JsonProperty(value = "temp_max")
	private double tempMax;

}
