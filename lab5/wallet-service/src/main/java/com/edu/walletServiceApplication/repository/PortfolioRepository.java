package com.edu.walletServiceApplication.repository;

import com.edu.walletServiceApplication.model.Portfolio;
import com.edu.walletServiceApplication.model.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByWalletIdAndSymbol(Long id, Symbol symbol);
    List<Portfolio> findAllByWalletId(Long id);
}
