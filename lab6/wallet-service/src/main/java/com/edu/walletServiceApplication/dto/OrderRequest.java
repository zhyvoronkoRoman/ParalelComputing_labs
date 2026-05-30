package com.edu.walletServiceApplication.dto;

import com.edu.walletServiceApplication.model.Symbol;

import java.math.BigDecimal;

public record OrderRequest(Long userId, Symbol symbol, BigDecimal amount, BigDecimal totalPrice) {
}
