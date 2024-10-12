package com.kagimbz.cloud_nine.service;

import com.kagimbz.cloud_nine.configs.OpenWeatherAppConfig;
import com.kagimbz.cloud_nine.dto.GeoCoding.GeoCodingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeoCodesService {
    private final WebClient.Builder webClientBuilder;
    private final OpenWeatherAppConfig openWeatherAppConfig;
//    @Cacheable(value = "geoCodesCache", key = "#location", unless = "#result == null")
    public Mono<List<GeoCodingResponse>> getGeoCodes(String location) {

        log.info("Fetching Geo Codes for {}", location);

        return webClientBuilder.build().get()
                .uri(uriBuilder -> uriBuilder
                        .path(openWeatherAppConfig.getGeoCodingApi())
                        .queryParam("q", location.trim())
                        .queryParam("appid", openWeatherAppConfig.getApiKey())
                        .build())
                .retrieve()
                .bodyToFlux(GeoCodingResponse.class)
                .collectList()
                .doOnNext(res -> log.info("Geo codes response received: {}", res.toString()))
                .onErrorResume(e -> {
                    log.error("Error when fetching geo codes: {}", e.getLocalizedMessage(), e);
                    return Mono.empty();
                });

    }
}
