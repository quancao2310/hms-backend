package com.example.hms.patientfacingservice.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatusCode status;

    public CustomException(String message, HttpStatusCode status) {
        super(message);
        this.status = status;
    }
}
