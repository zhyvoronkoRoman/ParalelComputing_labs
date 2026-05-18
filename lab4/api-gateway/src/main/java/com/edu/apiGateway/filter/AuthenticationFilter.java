package com.edu.apiGateway.filter;

import com.edu.apiGateway.JwtUtils.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtUtils jwtUtils;

    public AuthenticationFilter(JwtUtils jwtUtils) {
        super(Config.class);
        this.jwtUtils = jwtUtils;
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null) {
                throw new RuntimeException("Відсутній заголовок Authorization");
            }

            if (authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);

                if (!jwtUtils.isTokenValid(token)) {
                    throw new RuntimeException("Невалідний токен");
                }

                String userId = String.valueOf(jwtUtils.getClaims(token).get("userId"));

                ServerHttpRequest mutatedRequest = request.mutate()
                        .header("X-User-Id", userId)
                        .build();

                return chain.filter(exchange.mutate().request(mutatedRequest).build());
            } else {
                throw new RuntimeException("Неправильний формат токена");
            }
        };
    }
}