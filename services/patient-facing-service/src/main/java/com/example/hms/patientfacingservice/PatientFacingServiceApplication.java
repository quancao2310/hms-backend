package com.example.hms.patientfacingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = {"com.example.hms.models.external"})
public class PatientFacingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientFacingServiceApplication.class, args);
    }
}
