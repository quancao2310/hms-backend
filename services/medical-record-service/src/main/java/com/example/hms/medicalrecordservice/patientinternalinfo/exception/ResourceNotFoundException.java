package com.example.hms.medicalrecordservice.patientinternalinfo.exception;

import com.example.hms.medicalrecordservice.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CustomException {
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
