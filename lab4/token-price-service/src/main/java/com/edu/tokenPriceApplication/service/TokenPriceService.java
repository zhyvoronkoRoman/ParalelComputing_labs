package com.edu.tokenPriceApplication.service;

import com.edu.tokenPriceApplication.dto.Token;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TokenPriceService {
    private final RestClient restClient = RestClient.create();

    public List<Token> getPrice() {
        String urlString = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum&vs_currencies=usd";
        Map<String, Map<String, BigDecimal>> response = restClient.get().uri(urlString)
                .retrieve().body(new org.springframework.core.ParameterizedTypeReference<>() {
                });
        List<Token> tokens = new ArrayList<>();

        for (Map.Entry<String, Map<String, BigDecimal>> entry : response.entrySet()) {
            String coinName = entry.getKey();
            BigDecimal price = entry.getValue().get("usd");

            Token token = new Token(coinName, price);
            tokens.add(token);
        }
        tokens.add(new Token("tether", BigDecimal.ONE));
        return tokens;
    }

    public BigDecimal getPriceBySymbol(String symbol) {
        return getPrice().stream()
                .filter(p -> p.name().equalsIgnoreCase(symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Price for" + symbol + " not found"))
                .price();
    }

}