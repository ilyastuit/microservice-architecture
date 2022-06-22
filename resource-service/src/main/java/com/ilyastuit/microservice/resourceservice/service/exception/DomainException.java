package com.ilyastuit.microservice.resourceservice.service.exception;

public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}
