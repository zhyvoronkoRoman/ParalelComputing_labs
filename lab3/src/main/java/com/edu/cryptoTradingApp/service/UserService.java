package com.edu.cryptoTradingApp.service;

import com.edu.cryptoTradingApp.dto.response.PortfolioResponse;
import com.edu.cryptoTradingApp.model.Portfolio;
import com.edu.cryptoTradingApp.model.Symbol;
import com.edu.cryptoTradingApp.model.User;
import com.edu.cryptoTradingApp.model.Wallet;
import com.edu.cryptoTradingApp.repository.PortfolioRepository;
import com.edu.cryptoTradingApp.repository.WalletRepository;
import com.edu.cryptoTradingApp.utils.mapper.PortfolioMapper;
import com.edu.cryptoTradingApp.utils.exceptions.types.WalletNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final WalletRepository walletRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;

    public void createWallet(User user){
        Wallet w = new Wallet();
        w.setUser(user);
        w.setBalanceUsd(BigDecimal.valueOf(0.0));
        walletRepository.save(w);
        createPortfolio(w);
    }
    public void createPortfolio(Wallet wallet){
        Portfolio portfolio = new Portfolio();
        portfolio.setWallet(wallet);
        portfolio.setAmount(wallet.getBalanceUsd());
        portfolio.setSymbol(Symbol.USDT);
        portfolioRepository.save(portfolio);
    }

    public List<PortfolioResponse> getPortfolio(Long id) {
        List<Portfolio> portfolioList = portfolioRepository.findAllByWalletId(id);
        if (!portfolioList.isEmpty()) {
        List<PortfolioResponse> dto = new ArrayList<>();
        for (Portfolio p : portfolioList) {
            if(p.getSymbol() == Symbol.USDT){
                p.setAmount(p.getWallet().getBalanceUsd());
            }
            dto.add(portfolioMapper.toDTO(p));
        }

        return dto;
        }else throw new WalletNotFoundException(id);
    }
}
