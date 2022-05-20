package com.oddle.app.weather.dto.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_weather")
public class Weather {

	@Id
	private Long id;

	private String name;

	private String main;

	private String description;

	private double temperature;

	private double windSpeed;

	private double clouds;

	private long date;

}
