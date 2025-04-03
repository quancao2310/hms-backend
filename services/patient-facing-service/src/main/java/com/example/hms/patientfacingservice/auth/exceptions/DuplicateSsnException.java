package com.example.hms.patientfacingservice.auth.exceptions;

import com.example.hms.patientfacingservice.common.exceptions.CustomException;
import org.springframework.http.HttpStatus;

public class DuplicateSsnException extends CustomException {
    public DuplicateSsnException() {
        super("SSN already exists", HttpStatus.BAD_REQUEST);
    }
}
