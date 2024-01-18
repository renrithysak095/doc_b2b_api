package com.example.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/gateway-service/v3/api-docs").uri("lb://gateway-service"))
                .route(r -> r.path("/doc-service/v3/api-docs").uri("lb://doc-service"))
                .route(r -> r.path("/auth-service/v3/api-docs").uri("lb://auth-service"))
                .route(r -> r.path("/help-service/v3/api-docs").uri("lb://help-service"))
                .build();
    }
}
