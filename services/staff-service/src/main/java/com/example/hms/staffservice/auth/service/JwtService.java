package com.example.hms.staffservice.auth.service;

import com.example.hms.staffservice.auth.security.ExtendedUserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {
    String generateToken(ExtendedUserDetails userDetails);
    boolean validateToken(String token);
    String extractSubject(String token);
    String extractEmail(String token);
}
