package com.kagimbz.cloud_nine.dto.CurrentWeatherData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rain implements Serializable {

	@JsonProperty("1h")
	private double _1h;

	@JsonProperty("3h")
	private double _3h;
}