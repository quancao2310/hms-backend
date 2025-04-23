package com.example.hms.medicalrecordservice.patientinternalinfo.exception;

import com.example.hms.medicalrecordservice.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends CustomException {
    public ResourceAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
