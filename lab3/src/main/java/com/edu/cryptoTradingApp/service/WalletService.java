package com.edu.cryptoTradingApp.service;


import com.edu.cryptoTradingApp.model.Portfolio;
import com.edu.cryptoTradingApp.model.Wallet;
import com.edu.cryptoTradingApp.repository.PortfolioRepository;
import com.edu.cryptoTradingApp.repository.WalletRepository;
import com.edu.cryptoTradingApp.utils.exceptions.types.WalletNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class WalletService {
    private WalletRepository walletRepository;
    private PortfolioRepository portfolioRepository;
    TokenPriceService tokenPrice;

    @Transactional
    public void depositMoney(Long id, BigDecimal amount){
        Wallet wallet = walletRepository.findByUserId(id).orElseThrow(() -> new WalletNotFoundException(id));
        BigDecimal newBalance = wallet.getBalanceUsd().add(amount);
        wallet.setBalanceUsd(newBalance);
        walletRepository.save(wallet);
    }

    public BigDecimal getBalance(Long id){
        Wallet wallet = walletRepository.findByUserId(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
        List<Portfolio> portfolioList = portfolioRepository.findAllByWalletId(wallet.getId());
        BigDecimal balance = wallet.getBalanceUsd();
        for(Portfolio p : portfolioList){
            balance = balance.add((p.getAmount().multiply(tokenPrice.gepPriceBySymbol(p.getSymbol()))));
        }
        return balance;
    }
}
