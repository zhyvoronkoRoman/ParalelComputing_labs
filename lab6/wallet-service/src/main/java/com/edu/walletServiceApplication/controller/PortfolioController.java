package com.edu.walletServiceApplication.controller;

import com.edu.walletServiceApplication.dto.PortfolioResponse;
import com.edu.walletServiceApplication.service.PortfolioService;
import com.edu.walletServiceApplication.service.WalletService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/portfolio")
public class PortfolioController {
    private final WalletService walletService;
    private final PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<List<PortfolioResponse>> showUserPortfolio(@Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(portfolioService.getPortfolio(userId));
    }

    @GetMapping("/balance")
    public BigDecimal getBalance(@Parameter(hidden = true)@RequestHeader("X-User-Id") Long userId) {
        return walletService.getBalance(userId);
    }
}
