package com.example.hms.staffservice.auth.exception;

import com.example.hms.staffservice.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidJwtException extends CustomException {
    public InvalidJwtException() {
        super("JWT is invalid or expired", HttpStatus.UNAUTHORIZED);
    }
}
