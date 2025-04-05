package com.example.hms.staffservice;

import com.example.hms.enums.UserRole;
import com.example.hms.models.internal.staff.Admin;
import com.example.hms.models.internal.staff.Staff;
import com.example.hms.staffservice.staff.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = {"com.example.hms.models.internal"})
public class StaffServiceApplication {

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    public static void main(String[] args) {
        SpringApplication.run(StaffServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StaffRepository staffRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!staffRepository.existsByEmail(adminEmail)) {
                Staff admin = Admin.builder()
                        .email(adminEmail)
                        .password(passwordEncoder.encode(adminPassword))
                        .role(UserRole.ADMIN)
                        .build();
                staffRepository.save(admin);
            }
        };
    }
}
