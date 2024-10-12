package com.kagimbz.cloud_nine.service;

import com.kagimbz.cloud_nine.configs.OpenWeatherAppConfig;
import com.kagimbz.cloud_nine.dto.CurrentWeatherData.CurrentWeatherDataResponse;
import com.kagimbz.cloud_nine.dto.FiveDayForecastData.FiveDayForecastWeatherDataResponse;
import com.kagimbz.cloud_nine.dto.GeoCoding.GeoCodingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
    private final WebClient.Builder webClientBuilder;
    private final OpenWeatherAppConfig openWeatherAppConfig;
    private final GeoCodesService geoCodesService;

    @Cacheable(value = "currentWeatherDataCache", key = "#location", unless = "#result == null")
    public Mono<CurrentWeatherDataResponse> fetchCurrentWeatherData(String location) {

        return geoCodesService.getGeoCodes(location)
                .flatMap(geoCodingResponse -> {
                    if (geoCodingResponse == null || geoCodingResponse.isEmpty()) return Mono.empty();

                    Object lat = geoCodingResponse.get(0).getLat();
                    Object lon = geoCodingResponse.get(0).getLon();

                    log.info("Fetching Current Weather Data");

                    return webClientBuilder.build().get()
                            .uri(uriBuilder -> uriBuilder
                                    .path(openWeatherAppConfig.getCurrentWeatherDataApi())
                                    .queryParam("lat", lat)
                                    .queryParam("lon", lon)
                                    .queryParam("units", "metric")
                                    .queryParam("appid", openWeatherAppConfig.getApiKey())
                                    .build())
                            .retrieve()
                            .bodyToMono(CurrentWeatherDataResponse.class)
                            .doOnNext(res -> log.info("Current weather data response received: {}", res))
                            .onErrorResume(e -> {
                                log.error("Error when fetching current weather data: {}", e.getLocalizedMessage(), e);
                                return Mono.empty();
                            });

                })
                .onErrorResume(e -> {
                    log.error("Error when fetching current weather data: {}", e.getLocalizedMessage(), e);
                    return Mono.empty();
                });

    }

    @Cacheable(value = "fiveDayWeatherForecastDataCache", key = "#location", unless = "#result == null")
    public Mono<FiveDayForecastWeatherDataResponse> fetchFiveDayWeatherForecastData(String location) {

        return geoCodesService.getGeoCodes(location)
                .flatMap(geoCodingResponse -> {
                    if (geoCodingResponse == null || geoCodingResponse.isEmpty()) return Mono.empty();

                    Object lat = geoCodingResponse.get(0).getLat();
                    Object lon = geoCodingResponse.get(0).getLon();

                    log.info("Fetching Five-Day Weather Forecast Data");

                    return webClientBuilder.build().get()
                            .uri(uriBuilder -> uriBuilder
                                    .path(openWeatherAppConfig.getFiveDayForecastApi())
                                    .queryParam("lat", lat)
                                    .queryParam("lon", lon)
                                    .queryParam("units", "metric")
                                    .queryParam("appid", openWeatherAppConfig.getApiKey())
                                    .build())
                            .retrieve()
                            .bodyToMono(FiveDayForecastWeatherDataResponse.class)
                            .doOnNext(res -> log.info("Five-day weather forecast data response received: {}", res))
                            .onErrorResume(e -> {
                                log.error("Error when fetching five-day weather forecast data: {}", e.getLocalizedMessage(), e);
                                return Mono.empty();
                            });

                })
                .onErrorResume(e -> {
                    log.error("Error when fetching five-day weather forecast data: {}", e.getLocalizedMessage(), e);
                    return Mono.empty();
                });

    }

}
