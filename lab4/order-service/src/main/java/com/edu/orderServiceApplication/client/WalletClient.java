package com.edu.orderServiceApplication.client;


import com.edu.orderServiceApplication.model.Symbol;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "wallet-service")
public interface WalletClient {

    @PostMapping("/api/wallet/execute-trade")
    void executeTrade(@RequestParam("userId") Long userId,
                      @RequestParam("symbol") Symbol symbol,
                      @RequestParam("amount") BigDecimal amount,
                      @RequestParam("totalPrice") BigDecimal totalPrice,
                      @RequestParam("side") String side);
}
