package com.edu.cryptoTradingApp.utils.exceptions.types;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
