package com.example.hms.patientfacingservice.auth.exceptions;

import com.example.hms.patientfacingservice.common.exceptions.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidJwtException extends CustomException {
    public InvalidJwtException() {
        super("JWT is invalid or expired", HttpStatus.UNAUTHORIZED);
    }
}
