package com.kagimbz.cloud_nine.dto.FiveDayForecastData;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kagimbz.cloud_nine.dto.CurrentWeatherData.Coord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class City implements Serializable {

	@JsonProperty("country")
	private String country;

	@JsonProperty("coord")
	private Coord coord;

	@JsonProperty("sunrise")
	private long sunrise;

	@JsonProperty("timezone")
	private long timezone;

	@JsonProperty("sunset")
	private long sunset;

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private long id;

	@JsonProperty("population")
	private long population;

	public LocalDateTime getSunrise() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(sunrise), ZoneId.of("Africa/Nairobi"));
	}

	public LocalDateTime getSunset() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(sunset), ZoneId.of("Africa/Nairobi"));
	}
}