package com.kagimbz.cloud_nine.auth.configs;

import com.kagimbz.cloud_nine.auth.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.SecurityContextServerWebExchangeWebFilter;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final ReactiveAuthenticationManager reactiveAuthenticationManager;
    private final WebSessionSecurityContextRepository webSessionSecurityContextRepository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint((exchange, e) -> {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "*");
                            return exchange.getResponse()
                                    .writeWith(Mono.just(exchange.getResponse()
                                            .bufferFactory()
                                            .wrap("Authorization failure! Kindly ensure that your session has not expired.".getBytes())));
                        })
                        .accessDeniedHandler((exchange, e) -> {
                            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                            exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "*");
                            return exchange.getResponse()
                                    .writeWith(Mono.just(exchange.getResponse()
                                            .bufferFactory()
                                            .wrap("Access denied for the requested resource! Contact your system admin for assistance.".getBytes())));
                        })
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth -> auth
                        .pathMatchers("/api/v1/auth/create-account", "/api/v1/auth/login", "").permitAll()
                        .pathMatchers("/api/v1/weather/**").hasAnyAuthority("CUSTOMER", "ADMIN")
                        .anyExchange().authenticated()
                )
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .securityContextRepository(webSessionSecurityContextRepository)
                .authenticationManager(reactiveAuthenticationManager)
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);


        return http.build();
    }
}
