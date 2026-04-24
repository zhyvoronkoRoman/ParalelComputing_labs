package com.edu.cryptoTradingApp.controller;

import com.edu.cryptoTradingApp.model.User;
import com.edu.cryptoTradingApp.service.WalletService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/deposit")
    public ResponseEntity<Void> depositMoney(@AuthenticationPrincipal User currentUser,
                                             @Min(1) @RequestParam BigDecimal amount){
        walletService.depositMoney(currentUser.getId(), amount);
        return ResponseEntity.ok().build();
    }
}
