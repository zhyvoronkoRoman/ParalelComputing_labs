package com.edu.cryptoTradingApp.service;

import com.edu.cryptoTradingApp.model.*;
import com.edu.cryptoTradingApp.repository.PortfolioRepository;
import com.edu.cryptoTradingApp.repository.WalletRepository;
import com.edu.cryptoTradingApp.utils.exceptions.types.BadRequestException;
import com.edu.cryptoTradingApp.utils.exceptions.types.WalletNotFoundException;
import jakarta.transaction.Transactional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;

@Component
@Getter
@RequiredArgsConstructor
public abstract class ExecuteOrder {
    private final WalletRepository walletRepository;
    private final TokenPriceService tokenPriceService;
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public void buyCrypto(Long id, Symbol symbol, BigDecimal amount){
        Wallet wallet = walletRepository.findByUserId(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
        BigDecimal price = tokenPriceService.gepPriceBySymbol(symbol);
        BigDecimal totalCost = amount.multiply(price);
        if (wallet.getBalanceUsd().compareTo(totalCost)<0){
            throw new BadRequestException("Not enough USD");
        }
        wallet.setBalanceUsd(wallet.getBalanceUsd().subtract(totalCost));
        Portfolio asset = portfolioRepository.findByWalletIdAndSymbol(wallet.getId(), symbol)
                .orElse(new Portfolio(null,wallet, symbol, BigDecimal.ZERO));
        asset.setAmount(asset.getAmount().add(amount));
        walletRepository.save(wallet);
        portfolioRepository.save(asset);
    }

    @Transactional
    public void sellCrypto(Long id, Symbol symbol, BigDecimal amount){
        Wallet wallet = walletRepository.findByUserId(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
        Portfolio portfolio = portfolioRepository.findByWalletIdAndSymbol(wallet.getId(), symbol)
                .orElseThrow(() -> new BadRequestException("You do not have "+ symbol + "tokens"));
        if (portfolio.getAmount().compareTo(amount)<0){
            throw new BadRequestException("Not enough "+ symbol);
        }
        BigDecimal price = tokenPriceService.gepPriceBySymbol(symbol);
        BigDecimal profit = price.multiply(amount);
        portfolio.setAmount(portfolio.getAmount().subtract(amount));
        wallet.setBalanceUsd(wallet.getBalanceUsd().add(profit));
        walletRepository.save(wallet);
        if (portfolio.getAmount().compareTo(BigDecimal.ZERO)==0){
            portfolioRepository.delete(portfolio);
        }else portfolioRepository.save(portfolio);
    }
}
