package com.edu.cryptoTradingApp.dto.request;

import com.edu.cryptoTradingApp.model.OrderType;
import com.edu.cryptoTradingApp.model.Symbol;
import jakarta.validation.constraints.*;


import java.math.BigDecimal;

public record MarketOrderRequest(@NotNull(message = "type must not be empty")OrderType type,
                                 @DecimalMin(value = "0.0001") @Digits(integer = 20, fraction = 8) BigDecimal amount,
                                 @NotNull(message = "symbol must not be empty") Symbol symbol) {
}
