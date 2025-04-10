package com.example.hms.staffservice.auth.exception;

import com.example.hms.staffservice.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class DuplicateSsnException extends CustomException {
    public DuplicateSsnException() {
        super("SSN already exists", HttpStatus.BAD_REQUEST);
    }
}
