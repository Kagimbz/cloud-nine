package com.kagimbz.cloud_nine.dto.CurrentWeatherData;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Sys implements Serializable {

	@JsonProperty("country")
	private String country;

	@JsonProperty("sunrise")
	private long sunrise;

	@JsonProperty("sunset")
	private long sunset;

	@JsonProperty("id")
	private long id;

	@JsonProperty("type")
	private long type;

	@JsonProperty("pod")
	private String pod;

	public LocalDateTime getSunrise() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(sunrise), ZoneId.of("Africa/Nairobi"));
	}

	public LocalDateTime getSunset() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(sunset), ZoneId.of("Africa/Nairobi"));
	}
}