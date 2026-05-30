package com.edu.walletServiceApplication.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@FeignClient(name = "price-service")
public interface PriceClient {
    @GetMapping("/api/prices/current")
    BigDecimal gepPriceBySymbol(String symbol);
}
