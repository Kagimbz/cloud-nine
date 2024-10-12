package com.kagimbz.cloud_nine.dto.CurrentAndForecastWeather;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyItem{

	@JsonProperty("moonset")
	private long moonSet;

	@JsonProperty("summary")
	private String summary;

	@JsonProperty("rain")
	private double rain;

	@JsonProperty("sunrise")
	private long sunrise;

	@JsonProperty("temp")
	private Temp temp;

	@JsonProperty("moon_phase")
	private double moonPhase;

	@JsonProperty("uvi")
	private double uvi;

	@JsonProperty("moonrise")
	private long moonrise;

	@JsonProperty("pressure")
	private double pressure;

	@JsonProperty("clouds")
	private double clouds;

	@JsonProperty("feels_like")
	private FeelsLike feelsLike;

	@JsonProperty("wind_gust")
	private double windGust;

	@JsonProperty("dt")
	private long dt;

	@JsonProperty("pop")
	private double pop;

	@JsonProperty("wind_deg")
	private double windDeg;

	@JsonProperty("dew_point")
	private double dewPoint;

	@JsonProperty("sunset")
	private long sunset;

	@JsonProperty("weather")
	private List<WeatherItem> weather;

	@JsonProperty("humidity")
	private double humidity;

	@JsonProperty("wind_speed")
	private double windSpeed;

	public LocalDateTime getMoonSet() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(moonSet), ZoneId.of("Africa/Nairobi"));
	}

	public LocalDateTime getMoonrise() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(moonrise), ZoneId.of("Africa/Nairobi"));
	}

	public LocalDateTime getSunrise() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(sunrise), ZoneId.of("Africa/Nairobi"));
	}

	public LocalDateTime getSunset() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(sunset), ZoneId.of("Africa/Nairobi"));
	}

	public LocalDateTime getDt() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(dt), ZoneId.of("Africa/Nairobi"));
	}
}