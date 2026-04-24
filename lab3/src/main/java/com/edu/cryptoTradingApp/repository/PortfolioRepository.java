package com.edu.cryptoTradingApp.repository;

import com.edu.cryptoTradingApp.model.Portfolio;
import com.edu.cryptoTradingApp.model.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByWalletIdAndSymbol(Long id, Symbol symbol);
    List<Portfolio> findAllByWalletId(Long id);
}
