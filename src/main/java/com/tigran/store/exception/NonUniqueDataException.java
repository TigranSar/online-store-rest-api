package com.tigran.store.exception;

public class NonUniqueDataException extends RuntimeException{
    public NonUniqueDataException(String message) {
        super(message);
    }
}
