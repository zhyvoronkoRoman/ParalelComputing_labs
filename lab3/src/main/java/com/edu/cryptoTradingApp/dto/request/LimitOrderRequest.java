package com.edu.cryptoTradingApp.dto.request;

import com.edu.cryptoTradingApp.model.OrderType;
import com.edu.cryptoTradingApp.model.Symbol;
import jakarta.validation.constraints.*;
import org.springframework.security.web.server.ui.OneTimeTokenSubmitPageGeneratingWebFilter;

import java.math.BigDecimal;

public record LimitOrderRequest(@NotNull(message = "Target Price must not be empty")BigDecimal targetPrice,
                                @NotNull(message = "Order Type must not be empty")OrderType type,
                                @NotNull(message = "Symbol must not be empty") Symbol symbol,
                                @DecimalMin(value = "0.0001", inclusive = true) @Digits(integer = 20, fraction = 8) BigDecimal amount) {}
