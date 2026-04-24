package com.edu.cryptoTradingApp.dto.response;

import com.edu.cryptoTradingApp.model.User;

import java.math.BigDecimal;

public record WalletResponse(User user,
                             BigDecimal balanceUsd) {
}
