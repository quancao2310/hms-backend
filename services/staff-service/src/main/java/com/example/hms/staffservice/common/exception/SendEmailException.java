package com.example.hms.staffservice.common.exception;

import org.springframework.http.HttpStatus;

public class SendEmailException extends CustomException {
    public SendEmailException() {
        super("Failed to send email", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
