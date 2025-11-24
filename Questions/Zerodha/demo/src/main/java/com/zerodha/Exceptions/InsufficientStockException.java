package com.zerodha.Exceptions;

public class InsufficientStockException extends RuntimeException {
     public InsufficientStockException(String message) {
        super(message);
    }
}
