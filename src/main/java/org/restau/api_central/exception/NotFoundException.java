package org.restau.api_central.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Exception e) {
        super(e);
    }
    public NotFoundException(String message) {
        super(message);
    }
}