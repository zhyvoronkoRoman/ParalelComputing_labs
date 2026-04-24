package com.edu.cryptoTradingApp.controller;


import com.edu.cryptoTradingApp.dto.response.PortfolioResponse;
import com.edu.cryptoTradingApp.model.User;
import com.edu.cryptoTradingApp.service.UserService;
import com.edu.cryptoTradingApp.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {
    private final UserService userService;
    private final WalletService walletService;

    @GetMapping
    public ResponseEntity<List<PortfolioResponse>> showUserPortfolio(@AuthenticationPrincipal User currentUser){
        return ResponseEntity.ok(userService.getPortfolio(currentUser.getId()));
    }
    @GetMapping("/balance")
    public BigDecimal getBalance(@AuthenticationPrincipal User currentUser){
        return walletService.getBalance(currentUser.getId());
    }
}
