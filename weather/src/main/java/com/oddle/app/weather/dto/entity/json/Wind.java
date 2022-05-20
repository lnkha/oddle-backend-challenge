package com.oddle.app.weather.dto.entity.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Wind {

	@JsonProperty(value = "speed")
	private double speed;

	@JsonProperty(value = "deg")
	private double deg;
}
