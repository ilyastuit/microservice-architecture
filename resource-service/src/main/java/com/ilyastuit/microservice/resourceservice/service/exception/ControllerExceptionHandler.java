package com.ilyastuit.microservice.resourceservice.service.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERRORS_FIELD = "errors";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {

        Map<String, Map<String, String>> body = new HashMap<>();

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        (fieldError -> fieldError.getDefaultMessage()!= null ? fieldError.getDefaultMessage() : "Not Valid")
                ));

        body.put(ERRORS_FIELD, errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Map<String, String>>> constraintViolationException(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors= new HashMap<>();

        ex.getConstraintViolations()
                .forEach(cv -> errors.put(cv.getPropertyPath().toString(), cv.getMessage()));

        Map<String, Map<String, String>> result = new HashMap<>();
        result.put(ERRORS_FIELD, errors);

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> notFoundException(NotFoundException ex, WebRequest request) {
        Map<String, String> result = new HashMap<>();
        result.put(ERRORS_FIELD, ex.getMessage());

        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, String>> domainException(DomainException ex, WebRequest request) {
        Map<String, String> result = new HashMap<>();
        result.put(ERRORS_FIELD, ex.getMessage());

        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}