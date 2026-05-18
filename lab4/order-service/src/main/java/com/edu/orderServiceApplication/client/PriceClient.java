package com.edu.orderServiceApplication.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "price-service")
public interface PriceClient {
    @GetMapping("/api/prices/current")
    BigDecimal getPriceBySymbol(@RequestParam("symbol") String symbol);
}
