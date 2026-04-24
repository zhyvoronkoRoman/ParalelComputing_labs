package com.edu.cryptoTradingApp.dto.response;

import com.edu.cryptoTradingApp.model.Symbol;

public record PortfolioResponse(Symbol symbol,
                                double amount) {}
