package com.epam.hotelreservation.apigateway.config;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.HttpClientCustomizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.http.client.HttpClient;

@Configuration
public class GatewayConfig {

    @Autowired
    AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("guest-service", r -> r.path("/guests/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://guest-service"))

                .route("hotel-service", r -> r.path("/hotels/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://hotel-service"))

                .route("reservation-service", r -> r.path("/reservations/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://reservation-service"))

                .route("authentication-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://authentication-service"))
                .build();
    }

}
