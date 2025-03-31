package com.example.hms.patientfacingservice.auth.services;

import com.example.hms.patientfacingservice.auth.security.CustomUserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {
    String generateToken(CustomUserDetails userDetails);
    boolean validateToken(String token);
    String extractSubject(String token);
    String extractEmail(String token);
}
