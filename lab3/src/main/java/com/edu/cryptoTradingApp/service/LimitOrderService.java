package com.edu.cryptoTradingApp.service;

import com.edu.cryptoTradingApp.dto.request.LimitOrderRequest;
import com.edu.cryptoTradingApp.model.*;
import com.edu.cryptoTradingApp.repository.LimitOrderRepository;
import com.edu.cryptoTradingApp.repository.PortfolioRepository;
import com.edu.cryptoTradingApp.repository.WalletRepository;
import com.edu.cryptoTradingApp.utils.exceptions.types.BadRequestException;
import com.edu.cryptoTradingApp.utils.mapper.LimitOrderMapper;
import com.edu.cryptoTradingApp.utils.exceptions.types.WalletNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LimitOrderService extends ExecuteOrder {

    private final TokenPriceService tokenPriceService;
    private final LimitOrderRepository orderRepository;
    private final LimitOrderMapper limitOrderMapper;
    private final PortfolioRepository portfolioRepository;

    public LimitOrderService(
            WalletRepository walletRepository,
            TokenPriceService tokenPriceService,
            PortfolioRepository portfolioRepository,
            LimitOrderRepository orderRepository,
            LimitOrderMapper limitOrderMapper
    ) {
        super(walletRepository, tokenPriceService, portfolioRepository);
        this.orderRepository = orderRepository;
        this.limitOrderMapper = limitOrderMapper;
        this.tokenPriceService = tokenPriceService;
        this.portfolioRepository = portfolioRepository;
    }

    public void createLimitOrder(Long id, LimitOrderRequest order) {
        if ((order.amount().compareTo(BigDecimal.ZERO))<=0){
            throw new BadRequestException("Wrong amount");
        }
        Wallet wallet = getWalletRepository().findByUserId(id).
                orElseThrow(()->new WalletNotFoundException(id));
        BigDecimal price = tokenPriceService.gepPriceBySymbol(order.symbol());
        BigDecimal totalCost = order.amount().multiply(price);
        if (wallet.getBalanceUsd().compareTo(totalCost)<0){
            throw new BadRequestException("Not enough USD");
        }
        if (order.type() == OrderType.SELL){
        Portfolio portfolio = portfolioRepository.findByWalletIdAndSymbol(wallet.getId(), order.symbol())
                .orElseThrow(() -> new BadRequestException("You do not have "+ order.symbol() + " tokens"));
            if (portfolio.getAmount().compareTo(order.amount())<0){
                throw new BadRequestException("Not enough "+ order.symbol());
            }
        }
        LimitOrder limitOrder = limitOrderMapper.toEntity(order);
        limitOrder.setWallet(wallet);
        limitOrder.setStatus(Status.CREATED);
        orderRepository.save(limitOrder);
    }

    public boolean shouldExecuteOrder(LimitOrder order, BigDecimal currentPrice){
        int condition = currentPrice.compareTo(order.getTargetPrice());
        if(order.getType() == OrderType.BUY){
            return condition <=0 ;
        }
        if (order.getType() == OrderType.SELL){
            return condition >= 0;
        }
        return false;
    }
    public void executeOrder(LimitOrder order){
        if(order.getType() == OrderType.BUY){
            buyCrypto(order.getWallet().getUser().getId(), order.getSymbol(), order.getAmount());
        }
        if (order.getType() == OrderType.SELL) {
            sellCrypto(order.getWallet().getUser().getId(), order.getSymbol(), order.getAmount());
        }
    }

   @Scheduled(fixedRate = 10000)
    public void checkLimitOrder(){
        List<LimitOrder> openOrders = orderRepository.findAllByStatus(Status.CREATED);
        BigDecimal currentPrice;
        for (LimitOrder o : openOrders) {
            currentPrice = getTokenPriceService().gepPriceBySymbol(o.getSymbol());
            if (shouldExecuteOrder(o, currentPrice)) {
                executeOrder(o);
                o.setStatus(Status.FILLED);
                orderRepository.save(o);
            }
        }
    }
}
