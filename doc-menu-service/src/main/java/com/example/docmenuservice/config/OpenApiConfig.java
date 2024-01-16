package com.example.docmenuservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = @Server(url = "/", description = "Default Server URL"),
        info = @Info(
                title = "Doc Menu - Service",
                description = "Started From 15/Jan/2024",
                version = "1.0.0"
        )
)
public class OpenApiConfig {}
