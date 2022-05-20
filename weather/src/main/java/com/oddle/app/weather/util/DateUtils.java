package com.oddle.app.weather.util;

import java.time.*;

public class DateUtils {
	public static final String ZONE_ID_UTC = "UTC";

	public static ZonedDateTime getTodayMidnight() {
		final ZoneId utc = ZoneId.of(ZONE_ID_UTC);
		final LocalTime localTime = LocalTime.MIDNIGHT;
		final LocalDate localDate = LocalDate.now(utc);
		final LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		return ZonedDateTime.of(localDateTime, utc);
	}
}
