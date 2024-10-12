package com.kagimbz.cloud_nine.dto.CurrentWeatherData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clouds implements Serializable {

	@JsonProperty("all")
	private double all;
}