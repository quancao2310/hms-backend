package com.example.hms.patientfacingservice.common.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("Validation error occurred at {}: {}", ex.getClass().getName(), ex.getMessage(), ex);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed for one or more fields!",
                errors
        );
        return ResponseEntity.badRequest()
                .headers(headers)
                .body(errorResponse);
    }

//    @Override
//    protected ResponseEntity<Object> handleHandlerMethodValidationException(
//            HandlerMethodValidationException ex,
//            HttpHeaders headers,
//            HttpStatusCode status,
//            WebRequest request) {
//        log.error("Validation error occurred at {}: {}", ex.getClass().getName(), ex.getMessage(), ex);
//
//        String errorMessage = ex.getAllErrors()
//                .stream()
//                .map(MessageSourceResolvable::getDefaultMessage)
//                .collect(Collectors.joining(", "));
//
//        ErrorResponse errorResponse = new ErrorResponse(
//                status.value(),
//                errorMessage,
//                null
//        );
//        return ResponseEntity.status(status)
//                .headers(headers)
//                .body(errorResponse);
//    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        super.handleExceptionInternal(ex, body, headers, statusCode, request);
        log.error("{}: {}", ex.getClass().getName(), ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                statusCode.value(),
                (body instanceof ProblemDetail) ? ((ProblemDetail) body).getDetail() : ex.getMessage()
        );
        return ResponseEntity.status(statusCode)
                .headers(headers)
                .body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException ex,
            WebRequest request) {
        log.error("{}: {}", ex.getClass().getName(), ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Authentication failed! Please check your credentials."
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException ex,
            WebRequest request) {
        log.error("{}: {}", ex.getClass().getName(), ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "You are not authorized to access this resource!"
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(errorResponse);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(
            CustomException ex,
            WebRequest request) {
        log.error("{} with status {}: {}", ex.getClass().getSimpleName(), ex.getStatus().value(), ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getStatus().value(),
                ex.getMessage()
        );
        return ResponseEntity.status(ex.getStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Exception {}: {}", ex.getClass().getName(), ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred. Please try again later."
        );
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
