package com.example.hms.patientfacingservice.auth.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.hms.patientfacingservice.auth.security.ExtendedUserDetails;
import com.example.hms.patientfacingservice.auth.services.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private final String EMAIL_CLAIM = "email";

    @Override
    public String generateToken(ExtendedUserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getId().toString())
                .withClaim(EMAIL_CLAIM, userDetails.getEmail())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration))
                .sign(getAlgorithm());
    }

    @Override
    public boolean validateToken(String token) {
        try {
            decodeToken(token);
            return true;
        } catch (JWTVerificationException ex) {
            return false;
        }
    }

    @Override
    public String extractSubject(String token) {
        return decodeToken(token)
                .getSubject();
    }

    @Override
    public String extractEmail(String token) {
        return decodeToken(token)
                .getClaim(EMAIL_CLAIM)
                .asString();
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtSecret);
    }

    private DecodedJWT decodeToken(String token) {
        return JWT.require(getAlgorithm())
                .build()
                .verify(token);
    }
}
