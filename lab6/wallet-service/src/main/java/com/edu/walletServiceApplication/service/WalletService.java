package com.edu.walletServiceApplication.service;

import com.edu.walletServiceApplication.client.PriceClient;
import com.edu.walletServiceApplication.model.Portfolio;
import com.edu.walletServiceApplication.model.Symbol;
import com.edu.walletServiceApplication.model.Wallet;
import com.edu.walletServiceApplication.repository.PortfolioRepository;
import com.edu.walletServiceApplication.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final PortfolioRepository portfolioRepository;
    private final PriceClient client;

    @Transactional
    public void createUserWallet(Long userId){
        Wallet w = new Wallet();
        w.setUserId(userId);
        w.setBalanceUsd(BigDecimal.valueOf(0.0));
        walletRepository.save(w);

        Portfolio p = new Portfolio();
        p.setWallet(w);
        p.setAmount(BigDecimal.ZERO);
        p.setSymbol(Symbol.USDT);
        portfolioRepository.save(p);
        System.out.println("Wallet was successfully created!");
    }

    @Transactional
    public void depositMoney(Long id, BigDecimal amount){
        Wallet wallet = walletRepository.findByUserId(id)
                .orElseThrow(() -> new NoSuchElementException("wallet not found "));
        BigDecimal newBalance = wallet.getBalanceUsd().add(amount);
        wallet.setBalanceUsd(newBalance);
        walletRepository.save(wallet);
    }

    public BigDecimal getBalance(Long userId){
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("wallet not found "));
        List<Portfolio> portfolioList = portfolioRepository.findAllByWalletId(wallet.getId());
        BigDecimal balance = wallet.getBalanceUsd();
        for (Portfolio p : portfolioList){
            if(p.getSymbol() != Symbol.USDT){
                try {
                    BigDecimal currentPrice = client.gepPriceBySymbol(p.getSymbol().name());
                    balance = balance.add((p.getAmount().multiply(currentPrice)));
                }catch (Exception e){
                    System.err.println("Price not found " + e.getMessage());
                }
            }
        }
        return balance;
    }
}
