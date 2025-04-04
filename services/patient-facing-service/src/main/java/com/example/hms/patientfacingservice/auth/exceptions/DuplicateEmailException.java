package com.example.hms.patientfacingservice.auth.exceptions;

import com.example.hms.patientfacingservice.common.exceptions.CustomException;
import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends CustomException {
    public DuplicateEmailException() {
        super("Email already exists", HttpStatus.BAD_REQUEST);
    }
}
