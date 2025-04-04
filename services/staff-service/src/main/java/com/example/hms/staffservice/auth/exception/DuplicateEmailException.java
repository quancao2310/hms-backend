package com.example.hms.staffservice.auth.exception;

import com.example.hms.staffservice.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends CustomException {
    public DuplicateEmailException() {
        super("Email already exists", HttpStatus.BAD_REQUEST);
    }
}
