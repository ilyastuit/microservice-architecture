package com.ilyastuit.microservices.resourceprocessor.service.exception;

public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}
