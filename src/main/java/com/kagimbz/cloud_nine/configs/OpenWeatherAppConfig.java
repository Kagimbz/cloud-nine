package com.kagimbz.cloud_nine.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "open.weather")
@Data
public class OpenWeatherAppConfig {
    private String apiKey;

    private String baseUrl;

    private String geoCodingApi;

    private String oneCallApi3;

    private String currentWeatherDataApi;

    private String fiveDayForecastApi;
}
