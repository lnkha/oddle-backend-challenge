package com.oddle.app.weather.controller;

import com.oddle.app.weather.constant.RestPath;
import com.oddle.app.weather.dto.api.WeatherChangeRequest;
import com.oddle.app.weather.dto.api.WeatherResponse;
import com.oddle.app.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WeatherController {

	@Autowired
	WeatherService weatherService;

	@GetMapping(value = RestPath.Weather.TODAY_WEATHER)
	public ResponseEntity<WeatherResponse> getTodayWeather(@RequestParam(required = false) String city) {
		return ResponseEntity.ok(weatherService.getWeather(city));
	}

	@GetMapping(value = RestPath.Weather.HISTORY_WEATHER)
	public ResponseEntity<Page<WeatherResponse>> getHistoryWeather(@Min(value = 1, message = "Page must be higher than 1") @RequestParam(defaultValue = "1") final int page,
	                                                               @Min(value = 1, message = "PageSize must be higher than 1") @Max(value = 100, message = "PageSize must be in range 1-100") @RequestParam(defaultValue = "10") final int pageSize) {
		return ResponseEntity.ok(weatherService.getHistoryWeather(page, pageSize));
	}

	@PutMapping(value = RestPath.Weather.UPDATE_WEATHER)
	public ResponseEntity<Void> updateHistoryWeather(@RequestBody @Valid final WeatherChangeRequest weatherChangeRequest) {
		weatherService.updateWeather(weatherChangeRequest);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping(value = RestPath.Weather.DELETE_WEATHER)
	public ResponseEntity<Void> deleteHistoryWeather(@RequestParam long id) {
		weatherService.deleteWeather(id);
		return ResponseEntity.ok().build();
	}

}