package com.edu.walletServiceApplication.service;

import com.edu.walletServiceApplication.dto.PortfolioResponse;
import com.edu.walletServiceApplication.mapper.PortfolioMapper;
import com.edu.walletServiceApplication.model.Portfolio;
import com.edu.walletServiceApplication.model.Symbol;
import com.edu.walletServiceApplication.model.Wallet;
import com.edu.walletServiceApplication.repository.PortfolioRepository;
import com.edu.walletServiceApplication.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final WalletRepository walletRepository;

    public List<PortfolioResponse> getPortfolio(Long id) {
        Wallet wallet = walletRepository.findByUserId(id).orElseThrow(()-> new NoSuchElementException("wallet not found"));
        List<Portfolio> portfolioList = portfolioRepository.findAllByWalletId(wallet.getId());
        if (!portfolioList.isEmpty()) {
            List<PortfolioResponse> dto = new ArrayList<>();
            for (Portfolio p : portfolioList) {
                if(p.getSymbol() == Symbol.USDT){
                    p.setAmount(p.getWallet().getBalanceUsd());
                }
                dto.add(portfolioMapper.toDTO(p));
            }

            return dto;
        }else throw new NoSuchElementException("Portfolio not found");
    }
}
