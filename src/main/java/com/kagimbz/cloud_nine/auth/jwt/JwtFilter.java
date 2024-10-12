package com.kagimbz.cloud_nine.auth.jwt;

import com.kagimbz.cloud_nine.auth.users.UserRepo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements WebFilter {
    private final JwtService jwtService;
    private final ReactiveUserDetailsService reactiveUserDetailsService;
    private final UserRepo userRepo;

    @Override
    @NonNull
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        final String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.error("No JWT Token Present!");
            return chain.filter(exchange);
        }

        //Extract username
        final String jwt = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwt);

        if (!Objects.isNull(username)) {
            return reactiveUserDetailsService.findByUsername(username).flatMap(userDetails ->
                            userRepo.isLoggedIn(username)
                                    .filter(isLoggedIn -> isLoggedIn)
                                    .flatMap(isLoggedIn -> {
                                        log.info("User Is Logged In");

                                        if (!jwtService.tokenIsExpired(jwt)) {
                                            log.info("Token Not Expired");
                                            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, jwt, userDetails.getAuthorities());

                                            return exchange.getSession().flatMap(webSession -> {
                                                SecurityContext securityContext = (SecurityContext) webSession.getAttributes().get("SPRING_SECURITY_CONTEXT");

                                                if (securityContext == null) {
                                                    // If no SecurityContext in session, create and store it
                                                    securityContext = SecurityContextHolder.createEmptyContext();
                                                    securityContext.setAuthentication(token);
                                                    webSession.getAttributes().put("SPRING_SECURITY_CONTEXT", securityContext);
                                                }

                                                return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
                                            });

//                                            return ReactiveSecurityContextHolder.getContext()
//                                                    .defaultIfEmpty(SecurityContextHolder.createEmptyContext())
//                                                    .flatMap(securityContext -> {
//                                                        securityContext.setAuthentication(token);
//                                                        return chain.filter(exchange);
//                                                    });
                                        }

                                        return chain.filter(exchange);

                                    })
                    )
                    .onErrorResume(error -> {
                        log.error("Error when updating security context holder for user: " + username, error);
                        return chain.filter(exchange);
                    });

        }

        return chain.filter(exchange);

    }

}
