package com.kagimbz.cloud_nine.dto.CurrentWeatherData;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kagimbz.cloud_nine.dto.CurrentAndForecastWeather.WeatherItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentWeatherDataResponse implements Serializable {

	@JsonProperty("rain")
	private Rain rain;

	@JsonProperty("visibility")
	private double visibility;

	@JsonProperty("timezone")
	private long timezone;

	@JsonProperty("main")
	private Main main;

	@JsonProperty("clouds")
	private Clouds clouds;

	@JsonProperty("sys")
	private Sys sys;

	@JsonProperty("dt")
	private long dt;

	@JsonProperty("coord")
	private Coord coord;

	@JsonProperty("weather")
	private List<WeatherItem> weather;

	@JsonProperty("name")
	private String name;

	@JsonProperty("cod")
	private long cod;

	@JsonProperty("id")
	private long id;

	@JsonProperty("base")
	private String base;

	@JsonProperty("wind")
	private Wind wind;

	public LocalDateTime getDt() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(dt), ZoneId.of("Africa/Nairobi"));
	}
}