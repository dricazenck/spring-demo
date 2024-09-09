package com.wcc.springdemo.demo.config;

import com.wcc.springdemo.demo.exception.ProductIdDuplicatedException;
import com.wcc.springdemo.demo.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Global controller to handle all exceptions for the API.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Receive ContentNotFoundException and return {@link HttpStatus#NOT_FOUND}.
     */
    @ExceptionHandler({ProductNotFoundException.class, NoSuchElementException.class})
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<ErrorDetails> handleNotFoundException(
            final ProductNotFoundException ex, final WebRequest request) {
        final var errorDetails =
                new ErrorDetails(NOT_FOUND.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, NOT_FOUND);
    }

    @ExceptionHandler(ProductIdDuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handleProgramTypeError(
            final ProductIdDuplicatedException ex, final WebRequest request) {
        final var errorDetails =
                new ErrorDetails(
                        HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    public record ErrorDetails(int status, String message, String details) {
    }
}
