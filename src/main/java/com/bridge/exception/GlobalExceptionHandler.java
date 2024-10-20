package com.bridge.exception;

import com.bridge.response.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler extends Throwable{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessages.add(Objects.requireNonNull(error.getDefaultMessage()).trim());
        });

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(errorMessages);
        errorResponse.setStatus("error");
        errorResponse.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(List.of(ex.getMessage()));
        errorResponse.setStatus("error");
        errorResponse.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEnumValue(InvalidFormatException ex) {
        String fieldName = null;
        String message = "Invalid value provided";

        if (ex.getTargetType().isEnum()) {
            fieldName = ex.getPath().get(0).getFieldName();
            message = String.format("Invalid value '%s' for field '%s'", ex.getValue(), fieldName);
        }

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(List.of(message));
        errorResponse.setStatus("error");
        errorResponse.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = "Duplicate entry error";

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(List.of(message));
        errorResponse.setStatus("error");
        errorResponse.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
