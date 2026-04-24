package com.edu.cryptoTradingApp.controller;


import com.edu.cryptoTradingApp.dto.request.LimitOrderRequest;
import com.edu.cryptoTradingApp.dto.request.MarketOrderRequest;
import com.edu.cryptoTradingApp.model.User;
import com.edu.cryptoTradingApp.service.LimitOrderService;
import com.edu.cryptoTradingApp.service.MarketOrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final MarketOrderService marketOrdersService;
    private final LimitOrderService LimitOrdersService;

    @PostMapping("/limit")
    public ResponseEntity<Void> createLimitOrder(@AuthenticationPrincipal User currentUser,
                                                 @Valid @RequestBody LimitOrderRequest orderDto) {
        LimitOrdersService.createLimitOrder(currentUser.getId(), orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/market")
    public ResponseEntity<Void> createMarketOrder( @AuthenticationPrincipal User currentUser,
                                                  @Valid @RequestBody MarketOrderRequest orderDto) {
        marketOrdersService.createMarketOrder(currentUser.getId(), orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
