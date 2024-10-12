package com.kagimbz.cloud_nine.dto.CurrentAndForecastWeather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MinutelyItem{

	@JsonProperty("dt")
	private long dt;

	@JsonProperty("precipitation")
	private double precipitation;

	public LocalDateTime getDt() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(dt), ZoneId.of("Africa/Nairobi"));
	}
}