package com.epam.hotelreservation.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    AuthenticationFilter filter;

    public GatewayConfig(AuthenticationFilter filter) {
        this.filter = filter;
    }

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
