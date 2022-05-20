package com.oddle.app.weather.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RestPath {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Admin {
		public static final String ROOT = "/admin";

		//Get list city for client,impl later
		public static final String CITY = ROOT + "/city";
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Weather {
		public static final String ROOT = "/weather";
		public static final String TODAY_WEATHER = ROOT + "/today";
		public static final String HISTORY_WEATHER = ROOT + "/history";
		public static final String UPDATE_WEATHER = ROOT + "/update";
		public static final String DELETE_WEATHER = ROOT + "/delete";

		//Show favorite weather of user when open app, impl later
		public static final String FAVORITE_CITY = ROOT + "/favorite-city";

	}
}
