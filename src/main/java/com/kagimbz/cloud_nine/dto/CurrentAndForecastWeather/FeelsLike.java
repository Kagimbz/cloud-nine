package com.kagimbz.cloud_nine.dto.CurrentAndForecastWeather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeelsLike{

	@JsonProperty("eve")
	private double eve;

	@JsonProperty("night")
	private double night;

	@JsonProperty("day")
	private double day;

	@JsonProperty("morn")
	private double morn;
}