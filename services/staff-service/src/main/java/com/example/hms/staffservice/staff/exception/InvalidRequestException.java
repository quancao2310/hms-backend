package com.example.hms.staffservice.staff.exception;

import com.example.hms.staffservice.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidRequestException extends CustomException {
    public InvalidRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
