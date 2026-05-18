package com.edu.walletServiceApplication.service;

import com.edu.walletServiceApplication.dto.OrderRequest;
import com.edu.walletServiceApplication.model.Portfolio;
import com.edu.walletServiceApplication.model.Symbol;
import com.edu.walletServiceApplication.model.Wallet;
import com.edu.walletServiceApplication.repository.PortfolioRepository;
import com.edu.walletServiceApplication.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ExecuteTradeService {

    private final PortfolioRepository portfolioRepository;
    private final WalletRepository walletRepository;

    public void executeTrade(Long userId,Symbol symbol, BigDecimal amount, BigDecimal totalPrice,String side){
        OrderRequest request = new OrderRequest(userId, symbol, amount, totalPrice);
        if (side.equalsIgnoreCase("BUY")){
            buyCrypto(request);
        }else sellCrypto(request);
    }
    @Transactional
    public void buyCrypto(OrderRequest request){

        Wallet wallet = walletRepository.findByUserId(request.userId())
                .orElseThrow(() -> new NoSuchElementException("Wallet not found "));

               if (wallet.getBalanceUsd().compareTo(request.totalPrice())<0){throw new InvalidParameterException(" Invalid value");}
        wallet.setBalanceUsd(wallet.getBalanceUsd().subtract(request.totalPrice()));

        Portfolio asset = portfolioRepository.findByWalletIdAndSymbol(wallet.getId(), request.symbol())
                .orElse(new Portfolio(null,wallet, request.symbol(), BigDecimal.ZERO));
        asset.setAmount(asset.getAmount().add(request.amount()));

        walletRepository.save(wallet);
        portfolioRepository.save(asset);
    }

    @Transactional
    public void sellCrypto(OrderRequest request){
        Wallet wallet = walletRepository.findByUserId(request.userId())
                .orElseThrow(() -> new NoSuchElementException("Wallet not found "));

        Portfolio portfolio = portfolioRepository.findByWalletIdAndSymbol(wallet.getId(), request.symbol())
                .orElseThrow(() -> new NoSuchElementException("Wallet not found "));

        if (portfolio.getAmount().compareTo(request.amount())<0){
            throw new InvalidParameterException(" Invalid value");
        }

        portfolio.setAmount(portfolio.getAmount().subtract(request.amount()));
        wallet.setBalanceUsd(wallet.getBalanceUsd().add(request.totalPrice()));
        walletRepository.save(wallet);

        if (portfolio.getAmount().compareTo(BigDecimal.ZERO)==0){
            portfolioRepository.delete(portfolio);
        }else portfolioRepository.save(portfolio);
    }
}
