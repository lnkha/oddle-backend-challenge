package com.oddle.app.weather;

import com.oddle.app.weather.dto.api.WeatherResponse;
import com.oddle.app.weather.dto.entity.Weather;
import com.oddle.app.weather.exception.NotFoundException;
import com.oddle.app.weather.repository.WeatherRepository;
import com.oddle.app.weather.service.WeatherService;
import com.oddle.app.weather.util.DateUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class WeatherServiceTest {

	private static final String URL_CURRENT_WEATHER = "https://api.openweathermap.org/data/2.5/weather?q=dummyCity&appid=null";

	@Mock
	WeatherRepository weatherRepository;

	@Mock
	RestTemplate restTemplate;

	@Mock
	MessageSourceAccessor messages;

	@InjectMocks
	WeatherService weatherService;

	private String dummyCity;
	private Weather.WeatherBuilder weatherBuilder;
	private ResponseEntity<String> openWeatherResponse;
	private HttpEntity<String> openWeatherEntity;

	@Before
	public void setUp() {
		dummyCity = "dummyCity";
		long from = DateUtils.getTodayMidnight().toEpochSecond();
		long to = DateUtils.getTodayMidnight().plusDays(1).toEpochSecond();

		weatherBuilder = Weather.builder().id(1L)
				.temperature(123.4)
				.description("dummyDescription")
				.clouds(23)
				.main("dummyMain")
				.name("weather" + from)
				.date(from + to)
				.windSpeed(62);

		HttpHeaders openWeatherHeaders = new HttpHeaders();
		openWeatherEntity = new HttpEntity<>(openWeatherHeaders);
		String bodyCurrentWeather = "{\"coord\":{\"lon\":-0.1257,\"lat\":51.5085},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"base\":\"stations\",\"main\":{\"temp\":287.45,\"feels_like\":287.31,\"temp_min\":286.18,\"temp_max\":288.15,\"pressure\":1017,\"humidity\":91},\"visibility\":10000,\"wind\":{\"speed\":2.06,\"deg\":300},\"clouds\":{\"all\":100},\"dt\":1652940601,\"sys\":{\"type\":2,\"id\":2019646,\"country\":\"GB\",\"sunrise\":1652932997,\"sunset\":1652989855},\"timezone\":3600,\"id\":2643743,\"name\":\"London\",\"cod\":200},[Server:\"openresty\", Date:\"Thu, 19 May 2022 06:20:32 GMT\", Content-Type:\"application/json; charset=utf-8\", Content-Length:\"479\", Connection:\"keep-alive\", X-Cache-Key:\"/data/2.5/weather?q=london\", Access-Control-Allow-Origin:\"*\", Access-Control-Allow-Credentials:\"true\", Access-Control-Allow-Methods:\"GET, POST\"]>";
		openWeatherResponse = new ResponseEntity<>(bodyCurrentWeather, HttpStatus.OK);
	}

	@Test(expected = NullPointerException.class)
	public void returnExceptionWhenPutWrongCityInDBAndCurrentWeatherMap() {
		Optional<Weather> weather = Optional.empty();

		when(weatherRepository.findByNameAndDateBetween(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(weather);
		when(restTemplate.exchange(URL_CURRENT_WEATHER, HttpMethod.GET, openWeatherEntity, String.class)).thenReturn(null);
		weatherService.getWeather(dummyCity);
	}

	@Test()
	public void returnCorrectWeatherWhenCityInDB() {
		Optional<Weather> weather = Optional.ofNullable(weatherBuilder.build());

		when(weatherRepository.findByNameAndDateBetween(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(weather);
		WeatherResponse weatherResponse = weatherService.getWeather(dummyCity);
		assertEquals(weatherResponse.getName(), weather.get().getName());
	}

	@Test()
	public void returnCorrectWeatherWhenCityInCurrentWeatherMap() {
		Optional<Weather> weather = Optional.empty();

		when(weatherRepository.findByNameAndDateBetween(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(weather);
		when(restTemplate.exchange(URL_CURRENT_WEATHER, HttpMethod.GET, openWeatherEntity, String.class)).thenReturn(openWeatherResponse);
		WeatherResponse weatherResponse = weatherService.getWeather(dummyCity);

		assertEquals(weatherResponse.getName(), "London");
	}
}
