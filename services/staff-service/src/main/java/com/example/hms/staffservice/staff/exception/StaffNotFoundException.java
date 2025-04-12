package com.example.hms.staffservice.staff.exception;

import com.example.hms.staffservice.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class StaffNotFoundException extends CustomException {
    public StaffNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
