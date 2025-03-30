package com.example.hms.staffservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public OpenAPI staffServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("HMS Staff Service API Documentation")
                        .description("API Documentation for Staff Service in the Hospital Management System")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("HMS Support Team")
                                .email("support@hms-example.com"))
                        .license(new License().name("Private Use")))
                .servers(List.of(
                        new Server().url("/").description("Default Server URL")
                ));
    }
} 