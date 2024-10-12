package com.kagimbz.cloud_nine.dto.CurrentAndForecastWeather;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAndForecastWeatherDataResponse{

	@JsonProperty("alerts")
	private List<AlertsItem> alerts;

	@JsonProperty("current")
	private Current current;

	@JsonProperty("timezone")
	private String timezone;

	@JsonProperty("timezone_offset")
	private long timezoneOffset;

	@JsonProperty("daily")
	private List<DailyItem> daily;

	@JsonProperty("lon")
	private Object lon;

	@JsonProperty("hourly")
	private List<HourlyItem> hourly;

	@JsonProperty("minutely")
	private List<MinutelyItem> minutely;

	@JsonProperty("lat")
	private Object lat;
}