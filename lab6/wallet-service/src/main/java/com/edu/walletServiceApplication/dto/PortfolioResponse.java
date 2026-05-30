package com.edu.walletServiceApplication.dto;

import com.edu.walletServiceApplication.model.Symbol;

public record PortfolioResponse(Symbol symbol,
                                double amount) {}
