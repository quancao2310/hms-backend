package com.example.hms.staffservice.common.exception;

import com.example.hms.staffservice.common.dto.ErrorResponseDTO;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Validation error: {}", ex.getClass().getName(), ex);

        Map<String, Set<String>> errors = new HashMap<>();
        ex.getAllErrors().forEach((error) -> {
            String fieldName = "global";
            if (error instanceof FieldError fieldError) {
                fieldName = fieldError.getField();
            }
            String errorMessage = error.getDefaultMessage();
            errors.computeIfAbsent(fieldName, k -> new HashSet<>()).add(errorMessage);
        });

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                status.value(),
                "Validation failed for one or more fields!",
                errors
        );
        return ResponseEntity.status(status)
                .headers(headers)
                .body(errorResponseDTO);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ResponseEntity<Object> init = super.handleExceptionInternal(ex, body, headers, statusCode, request);
        log.error("Internal Exception: {}", ex.getClass().getName(), ex);

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                statusCode.value(),
                (init != null && init.getBody() instanceof ProblemDetail initBody) ? initBody.getDetail() : ex.getMessage()
        );
        return ResponseEntity.status(statusCode)
                .headers(headers)
                .body(errorResponseDTO);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthenticationException(
            AuthenticationException ex,
            WebRequest request) {
        log.error("Authentication Exception: {}", ex.getClass().getName(), ex);

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                HttpStatus.UNAUTHORIZED.value(),
                "Authentication failed! Please check your credentials."
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDTO);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(
            AccessDeniedException ex,
            WebRequest request) {
        log.error("Authorization Exception: {}", ex.getClass().getName(), ex);

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                HttpStatus.FORBIDDEN.value(),
                "You are not authorized to access this resource!"
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponseDTO);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomException(CustomException ex, WebRequest request) {
        log.error("{} occurred with status {}", ex.getClass().getName(), ex.getStatus().value(), ex);

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                ex.getStatus().value(),
                ex.getMessage()
        );
        return ResponseEntity.status(ex.getStatus()).body(errorResponseDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Unexpected Exception occurred: {}", ex.getClass().getName(), ex);

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred. Please try again later."
        );
        return ResponseEntity.internalServerError().body(errorResponseDTO);
    }
}
