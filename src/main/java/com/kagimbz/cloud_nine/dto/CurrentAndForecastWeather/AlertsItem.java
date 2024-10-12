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
public class AlertsItem{

	@JsonProperty("start")
	private long start;

	@JsonProperty("description")
	private String description;

	@JsonProperty("sender_name")
	private String senderName;

	@JsonProperty("end")
	private long end;

	@JsonProperty("event")
	private String event;

	@JsonProperty("tags")
	private List<Object> tags;

	public LocalDateTime getStart() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(start), ZoneId.of("Africa/Nairobi"));
	}

	public LocalDateTime getEnd() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(end), ZoneId.of("Africa/Nairobi"));
	}
}