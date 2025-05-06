package com.example.hms.aiservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = {"com.example.hms.models.internal"})
public class AiServiceApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(AiServiceApplication.class, args);
    }
}
