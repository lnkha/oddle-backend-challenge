package com.oddle.app.weather.dto.entity.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coordinate {

	@JsonProperty(value = "lon")
	private double lon;

	@JsonProperty(value = "lat")
	private double lat;
}
