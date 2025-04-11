package com.example.hms.appointmentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = {"com.example.hms.models.internal"})
@EnableScheduling
public class AppointmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppointmentServiceApplication.class, args);
    }
}
