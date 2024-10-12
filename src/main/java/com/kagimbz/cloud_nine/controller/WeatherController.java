package com.kagimbz.cloud_nine.controller;

import com.kagimbz.cloud_nine.dto.CurrentWeatherData.CurrentWeatherDataResponse;
import com.kagimbz.cloud_nine.dto.FiveDayForecastData.FiveDayForecastWeatherDataResponse;
import com.kagimbz.cloud_nine.dto.GeoCoding.GeoCodingResponse;
import com.kagimbz.cloud_nine.service.GeoCodesService;
import com.kagimbz.cloud_nine.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/weather")
@RequiredArgsConstructor
@Slf4j
public class WeatherController {
    private final WeatherService weatherService;
    private final GeoCodesService geoCodesService;

    @GetMapping(path = "/current-data")
    public ResponseEntity<Mono<CurrentWeatherDataResponse>> fetchCurrentWeatherData(@RequestParam String location) {
        return ResponseEntity.ok(weatherService.fetchCurrentWeatherData(location));
    }

    @GetMapping(path = "/five-day-forecast")
    public ResponseEntity<Mono<FiveDayForecastWeatherDataResponse>> fetchFiveDayWeatherForecastData(@RequestParam String location) {
        return ResponseEntity.ok(weatherService.fetchFiveDayWeatherForecastData(location));
    }

    @GetMapping(path = "/geo-location")
    public ResponseEntity<Mono<List<GeoCodingResponse>>> fetchGeoCodes(@RequestParam String location) {
        return ResponseEntity.ok(geoCodesService.getGeoCodes(location));
    }

}
