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
public class Current{

	@JsonProperty("sunrise")
	private long sunrise;

	@JsonProperty("temp")
	private double temp;

	@JsonProperty("visibility")
	private double visibility;

	@JsonProperty("uvi")
	private double uvi;

	@JsonProperty("pressure")
	private double pressure;

	@JsonProperty("clouds")
	private double clouds;

	@JsonProperty("feels_like")
	private double feelsLike;

	@JsonProperty("wind_gust")
	private double windGust;

	@JsonProperty("dt")
	private long dt;

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