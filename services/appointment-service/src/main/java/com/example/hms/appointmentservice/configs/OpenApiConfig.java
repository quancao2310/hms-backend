package com.example.hms.appointmentservice.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI staffServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HMS Appointment Service API")
                        .description("API documentation for Hospital Management System Appointment Service")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("HMS Support")
                                .email("support@hms.example.com"))
                        .license(new License()
                                .name("HMS License")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer")));
    }
}
