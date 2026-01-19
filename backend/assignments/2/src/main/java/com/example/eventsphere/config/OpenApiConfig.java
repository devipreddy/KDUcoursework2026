package com.example.eventsphere.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        SecurityScheme jwtScheme = new SecurityScheme()
                .name("jwt")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .addSecurityItem(
                        new SecurityRequirement().addList("jwt")
                )
                .components(
                        new Components().addSecuritySchemes("jwt", jwtScheme)
                );
    }
}
