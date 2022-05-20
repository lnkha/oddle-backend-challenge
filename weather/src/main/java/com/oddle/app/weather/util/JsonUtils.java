package com.oddle.app.weather.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {

	private static final ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

	@SneakyThrows
	public static String toJson(Object value) {
		return mapper.writeValueAsString(value);
	}

	public static <T> T toValue(String json, Class<T> t) throws JsonProcessingException {
		return mapper.readValue(json, t);
	}
}