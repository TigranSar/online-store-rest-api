package com.online.store.exception;

public class InvalidProductStatusTransitionException extends RuntimeException {
    public InvalidProductStatusTransitionException(String message) {
        super(message);
    }
}
