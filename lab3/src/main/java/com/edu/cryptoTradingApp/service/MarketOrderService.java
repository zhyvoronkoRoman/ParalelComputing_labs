package com.edu.cryptoTradingApp.service;

import com.edu.cryptoTradingApp.dto.request.MarketOrderRequest;
import com.edu.cryptoTradingApp.model.OrderType;
import com.edu.cryptoTradingApp.repository.PortfolioRepository;
import com.edu.cryptoTradingApp.repository.WalletRepository;
import com.edu.cryptoTradingApp.utils.exceptions.types.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MarketOrderService extends ExecuteOrder {

    public MarketOrderService(
            WalletRepository walletRepository,
            TokenPriceService tokenPriceService,
            PortfolioRepository portfolioRepository
    ) {
        super(walletRepository, tokenPriceService, portfolioRepository);
    }

    public void createMarketOrder(Long id, MarketOrderRequest order){
        /*if ((order.amount().compareTo(BigDecimal.ZERO))<=0){
            throw new BadRequestException("Wrong amount");
        }*/
        if(order.type() == OrderType.BUY){
            buyCrypto(id, order.symbol(), order.amount());
        } else if (order.type() == OrderType.SELL) {
            sellCrypto(id, order.symbol(), order.amount());
        }
    }
}
