package com.oddle.app.weather.service;

import com.oddle.app.weather.deserializer.CurrentWeatherDeserializer;
import com.oddle.app.weather.dto.api.WeatherChangeRequest;
import com.oddle.app.weather.dto.api.WeatherResponse;
import com.oddle.app.weather.dto.entity.Weather;
import com.oddle.app.weather.exception.MessageConstants;
import com.oddle.app.weather.exception.NotFoundException;
import com.oddle.app.weather.repository.WeatherRepository;
import com.oddle.app.weather.util.DateUtils;
import com.oddle.app.weather.util.JsonUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WeatherService {

	private static final String openWeatherMapURL = "https://api.openweathermap.org/data/2.5/weather?q=";
	private static final String appApiKey = "&appid=";

	@Value(value = "${open.weather.api.key}")
	private String apiKey;

	@Qualifier
	@Autowired
	MessageSourceAccessor messages;

	@NonNull
	@Autowired
	WeatherRepository weatherRepository;

	@NonNull
	private final RestTemplate restTemplate;


	@Transactional
	public WeatherResponse getWeather(String city) {

		final long todayMidnight = DateUtils.getTodayMidnight().toEpochSecond();
		final long tomorrowMidnight = DateUtils.getTodayMidnight().plusDays(1).toEpochSecond();

		Optional<Weather> openWeather = weatherRepository.findByNameAndDateBetween(city, todayMidnight, tomorrowMidnight);
		if (openWeather.isPresent()) {
			return convertWeatherToWeatherResponse(openWeather.get(), null);
		} else {
			try {
				restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
				HttpHeaders openWeatherHeaders = new HttpHeaders();
				HttpEntity<String> openWeatherEntity = new HttpEntity<>(openWeatherHeaders);
				final ResponseEntity<String> openWeatherResponse = restTemplate.exchange(
						openWeatherMapURL + city + appApiKey + apiKey,
						HttpMethod.GET, openWeatherEntity, String.class);

				CurrentWeatherDeserializer currentWeatherDeserializer = JsonUtils.toValue(openWeatherResponse.getBody(), CurrentWeatherDeserializer.class);

				weatherRepository.save(Weather.builder().id(currentWeatherDeserializer.getId())
						.name(currentWeatherDeserializer.getName())
						.temperature(currentWeatherDeserializer.getMain().getTemp())
						.main(currentWeatherDeserializer.getWeather().get(0).getMain())
						.description(currentWeatherDeserializer.getWeather().get(0).getDescription())
						.windSpeed(currentWeatherDeserializer.getWind().getSpeed())
						.clouds(currentWeatherDeserializer.getClouds().getAll())
						.date(currentWeatherDeserializer.getDate()).build());

				return convertWeatherToWeatherResponse(null, currentWeatherDeserializer);
			} catch (Exception e) {
				log.error(e.getMessage());
				throw new NotFoundException(messages.getMessage(MessageConstants.CITY_NOT_FOUND));
			}

		}
	}

	public Page<WeatherResponse> getHistoryWeather(final int page, final int pageSize) {
		return weatherRepository.findAll(PageRequest.of(page - 1, pageSize))
				.map((Weather weathers) -> convertWeatherToWeatherResponse(weathers, null));
	}

	@Transactional
	public void deleteWeather(long id) {
		weatherRepository.deleteById(id);
	}

	@Transactional
	public void updateWeather(WeatherChangeRequest weatherChangeRequest) {
		Weather weather = getById(weatherChangeRequest.getId());
		weather.setMain(weatherChangeRequest.getMain());
		weather.setDescription(weatherChangeRequest.getDescription());
		weather.setTemperature(weatherChangeRequest.getTemperature());
		weather.setWindSpeed(weather.getWindSpeed());
		weather.setClouds(weather.getClouds());
	}

	private WeatherResponse convertWeatherToWeatherResponse(Weather weathers, CurrentWeatherDeserializer currentWeatherDeserializer) {
		if (Objects.nonNull(weathers)) {
			return WeatherResponse.builder().id(weathers.getId())
					.name(weathers.getName())
					.temperature(weathers.getTemperature())
					.main(weathers.getMain())
					.description(weathers.getDescription())
					.wind(weathers.getWindSpeed())
					.clouds(weathers.getClouds())
					.date(weathers.getDate()).build();
		} else {
			return WeatherResponse.builder().id(currentWeatherDeserializer.getId())
					.name(currentWeatherDeserializer.getName())
					.temperature(currentWeatherDeserializer.getMain().getTemp())
					.main(currentWeatherDeserializer.getWeather().get(0).getMain())
					.description(currentWeatherDeserializer.getWeather().get(0).getDescription())
					.wind(currentWeatherDeserializer.getWind().getSpeed())
					.clouds(currentWeatherDeserializer.getClouds().getAll())
					.date(currentWeatherDeserializer.getDate()).build();
		}
	}

	private Weather getById(long id) {
		return weatherRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(messages.getMessage(MessageConstants.WEATHER_NOT_FOUND)));
	}
}
