package com.oddle.app.weather.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
	private final Long timestamp = Instant.now().toEpochMilli();
	private Integer status;
	private String errorCode;
	private List<String> errors;
	private Map<String, Object> attributes;
}
