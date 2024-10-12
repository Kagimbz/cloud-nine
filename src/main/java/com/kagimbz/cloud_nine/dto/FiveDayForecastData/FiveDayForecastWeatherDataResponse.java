package com.kagimbz.cloud_nine.dto.FiveDayForecastData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiveDayForecastWeatherDataResponse implements Serializable {

	@JsonProperty("city")
	private City city;

	@JsonProperty("cnt")
	private int cnt;

	@JsonProperty("cod")
	private String cod;

	@JsonProperty("message")
	private int message;

	@JsonProperty("list")
	private List<ListItem> list;
}