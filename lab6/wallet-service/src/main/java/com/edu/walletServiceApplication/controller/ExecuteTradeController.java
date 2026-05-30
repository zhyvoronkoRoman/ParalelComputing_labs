package com.edu.walletServiceApplication.controller;

import com.edu.walletServiceApplication.model.Symbol;
import com.edu.walletServiceApplication.service.ExecuteTradeService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
@Hidden
@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class ExecuteTradeController {

    private final ExecuteTradeService service;

    @PostMapping("/execute-trade")
    public void executeTrade(@RequestParam("userId") Long userId,
                      @RequestParam("symbol") Symbol symbol,
                      @RequestParam("amount") BigDecimal amount,
                      @RequestParam("totalPrice") BigDecimal totalPrice,
                      @RequestParam("side") String side){
        service.executeTrade(userId,symbol, amount, totalPrice, side);
    }

}
