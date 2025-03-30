package com.example.hms.staffservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Allow Swagger UI and API docs endpoints
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                // Swagger UI endpoints
                .requestMatchers("/v3/api-docs/**", "/v3/api-docs.yaml", "/api-docs/**", "/api-docs.yaml").permitAll()
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/webjars/**").permitAll()
                // Since we're in development, permit all requests for now
                .anyRequest().permitAll()
            );
            
        return http.build();
    }
} 