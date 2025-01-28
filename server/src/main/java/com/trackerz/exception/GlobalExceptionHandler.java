package com.trackerz.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // For validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> {
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
                    return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                }
                return error.getDefaultMessage();
            })
            .collect(Collectors.toList());

        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Data validation error",
            LocalDateTime.now(),
            details
        );
        
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // For Data integrity violation exception (unique constraint)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message;
        
        if (ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolation = (ConstraintViolationException) ex.getCause();

            // Check if it is a constraint violation on the product's name
            if (constraintViolation.getConstraintName() != null && 
                constraintViolation.getMessage().toLowerCase().contains("(name)")) {
                message = "Object with this name already exists";
            } else {
                message = "Database constraint violation";
            }
        } else {
            message = "Data integrity error";
        }

        ErrorResponse error = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            message,
            LocalDateTime.now(),
            Collections.singletonList(message)
        );
        
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // For resources not found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(EntityNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now(),
            Collections.emptyList()
        );
        
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // For object that already exists (should be unique)
    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleObjectAlreadyExistsException(ObjectAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.ALREADY_REPORTED.value(),
            ex.getMessage(),
            LocalDateTime.now(),
            Collections.emptyList()
        );
        
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // For other unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "An unexpected error occurred",
            LocalDateTime.now(),
            Collections.singletonList(ex.getMessage())
        );
        
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
} 