package com.kagimbz.cloud_nine.dto.FiveDayForecastData;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kagimbz.cloud_nine.dto.CurrentAndForecastWeather.WeatherItem;
import com.kagimbz.cloud_nine.dto.CurrentWeatherData.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListItem implements Serializable {

	@JsonProperty("dt")
	private int dt;

	@JsonProperty("pop")
	private double pop;

	@JsonProperty("visibility")
	private double visibility;

	@JsonProperty("dt_txt")
	private String dtTxt;

	@JsonProperty("weather")
	private List<WeatherItem> weather;

	@JsonProperty("main")
	private Main main;

	@JsonProperty("clouds")
	private Clouds clouds;

	@JsonProperty("sys")
	private Sys sys;

	@JsonProperty("wind")
	private Wind wind;

	@JsonProperty("rain")
	private Rain rain;

	public LocalDateTime getDt() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(dt), ZoneId.of("Africa/Nairobi"));
	}
}