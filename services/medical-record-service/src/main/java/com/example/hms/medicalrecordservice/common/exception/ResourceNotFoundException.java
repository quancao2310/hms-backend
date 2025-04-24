package com.example.hms.medicalrecordservice.common.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CustomException {
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
