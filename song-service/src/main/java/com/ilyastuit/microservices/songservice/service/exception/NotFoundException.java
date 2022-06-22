package com.ilyastuit.microservices.songservice.service.exception;

public class NotFoundException  extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
