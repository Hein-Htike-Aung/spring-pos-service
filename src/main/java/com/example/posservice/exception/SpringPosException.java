package com.example.posservice.exception;

public class SpringPosException extends RuntimeException {

    public SpringPosException(String message) {
        super(message);
    }

    public SpringPosException(String message, Throwable e) {
        super(message, e);
    }
}
