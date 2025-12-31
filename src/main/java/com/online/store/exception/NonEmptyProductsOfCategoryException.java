package com.online.store.exception;

public class NonEmptyProductsOfCategoryException extends RuntimeException {
    public NonEmptyProductsOfCategoryException(String message) {
        super(message);
    }
}
