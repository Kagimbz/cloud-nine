package com.kagimbz.cloud_nine.auth.configs;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class WebSessionSecurityContextRepository implements ServerSecurityContextRepository {
    private static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return exchange.getSession()
                .flatMap(webSession -> {
                    SecurityContext securityContext = (SecurityContext) webSession.getAttributes().get(SPRING_SECURITY_CONTEXT);
                    if (securityContext != null) {
                        return Mono.just(securityContext);
                    }
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return exchange.getSession()
                .doOnNext(webSession -> webSession.getAttributes().put(SPRING_SECURITY_CONTEXT, context))
                .then();
    }
}
