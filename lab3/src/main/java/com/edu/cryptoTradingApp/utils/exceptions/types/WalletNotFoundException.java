package com.edu.cryptoTradingApp.utils.exceptions.types;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(Long id) {
        super("Wallet with id "+id+" not found");
    }
}
