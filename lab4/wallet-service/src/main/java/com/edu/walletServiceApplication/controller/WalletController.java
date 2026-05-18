package com.edu.walletServiceApplication.controller;

import com.edu.walletServiceApplication.service.WalletService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallet")
public class WalletController {
    private final WalletService walletService;

    @Hidden
    @PostMapping("/create")
    void createWallet(@RequestParam("userId")Long userId){walletService.createUserWallet(userId);}

    @PostMapping("/deposit")
    public ResponseEntity<Void> depositMoney(@Parameter(hidden = true) @RequestHeader("X-User-Id")Long userId,
                                             @Min(1) @RequestParam BigDecimal amount){
        walletService.depositMoney(userId, amount);
        return ResponseEntity.ok().build();
    }
}
