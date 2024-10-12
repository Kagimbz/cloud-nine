package com.kagimbz.cloud_nine.dto.CurrentWeatherData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Main implements Serializable {

	@JsonProperty("temp")
	private double temp;

	@JsonProperty("temp_min")
	private double tempMin;

	@JsonProperty("grnd_level")
	private double groundLevel;

	@JsonProperty("temp_kf")
	private double tempKf;

	@JsonProperty("humidity")
	private double humidity;

	@JsonProperty("pressure")
	private double pressure;

	@JsonProperty("sea_level")
	private double seaLevel;

	@JsonProperty("feels_like")
	private double feelsLike;

	@JsonProperty("temp_max")
	private double tempMax;
}