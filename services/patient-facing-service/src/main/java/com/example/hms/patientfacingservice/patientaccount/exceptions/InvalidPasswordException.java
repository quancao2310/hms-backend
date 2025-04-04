package com.example.hms.patientfacingservice.patientaccount.exceptions;

import com.example.hms.patientfacingservice.common.exceptions.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends CustomException {
    public InvalidPasswordException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
