package com.online.store.exception;

public class OrderStatusChangeNotAllowedException extends RuntimeException {
    public OrderStatusChangeNotAllowedException(String message) {
        super(message);
    }
}
