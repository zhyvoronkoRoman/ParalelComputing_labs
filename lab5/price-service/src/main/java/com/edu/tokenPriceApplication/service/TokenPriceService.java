package com.edu.tokenPriceApplication.service;

import com.edu.tokenPriceApplication.dto.Token;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "tokens", key = "'all'")
    public List<Token> getPrice() {
        System.out.println("Fetching prices from external API (CoinGecko)...");
        /*String urlString = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum&vs_currencies=usd";
        Map<String, Map<String, BigDecimal>> response = restClient.get().uri(urlString)
                .retrieve().body(new org.springframework.core.ParameterizedTypeReference<>(){});
        List<Token> tokens = new ArrayList<>();

        for (Map.Entry<String, Map<String, BigDecimal>> entry : response.entrySet()) {
            String coinName = entry.getKey();
            BigDecimal price = entry.getValue().get("usd");

            Token token = new Token(coinName, price);
            tokens.add(token);
        }
        tokens.add(new Token("tether",BigDecimal.ONE));
        return tokens;*/

        BigDecimal randomBtc = BigDecimal.valueOf(90000 + (/*Math.random() * 5000 -*/ 2000));
        BigDecimal randomEth =BigDecimal.valueOf( 2300 /*+ (Math.random() * 200 - 100)*/);

        return List.of(
                new Token("tether", BigDecimal.ONE),
                new Token("bitcoin", randomBtc),
                new Token("ethereum", randomEth)
        );
    }
    @Cacheable(value = "tokenPrice", key = "#symbol.toLowerCase()")
    public BigDecimal getPriceBySymbol(String symbol){
        System.out.println("Calculating price for symbol: " + symbol);
        return getPrice().stream()
                .filter(p->p.name().equalsIgnoreCase(symbol))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Price for" + symbol +" not found"))
                .price();
    }

    @Scheduled(fixedRate = 1000 * 70)
    @CacheEvict(value = {"tokens", "tokenPrice"}, allEntries = true)
    public void fetchAndLogPrices() {
        List<Token> prices = getPrice();
        System.out.println("---- Оновлення цін: " + java.time.LocalDateTime.now() + " ----");
        prices.forEach(token ->
                System.out.println(token.name() + ": $" + token.price())
        );
    }
}