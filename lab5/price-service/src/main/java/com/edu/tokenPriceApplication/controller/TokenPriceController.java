package com.edu.tokenPriceApplication.controller;

import com.edu.tokenPriceApplication.service.TokenPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class TokenPriceController {
    private final TokenPriceService tokenPriceService;

    @GetMapping("/current")
    public BigDecimal getPriceBySymbol(@RequestParam("symbol") String symbol){
        return tokenPriceService.getPriceBySymbol(symbol);
    }

}
