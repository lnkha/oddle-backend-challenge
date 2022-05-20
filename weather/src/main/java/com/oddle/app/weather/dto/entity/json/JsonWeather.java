package com.oddle.app.weather.dto.entity.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonWeather {

	@JsonProperty(value = "id")
	private long id;

	@JsonProperty(value = "main")
	private String main;

	@JsonProperty(value = "description")
	private String description;

	@JsonProperty(value = "icon")
	private String icon;

}
