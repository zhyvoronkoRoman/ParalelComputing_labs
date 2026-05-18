package com.edu.walletServiceApplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wallets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", unique = true)
    private Long userId;
    @Column(name = "balance_usd")
    private BigDecimal balanceUsd;
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    List<Portfolio> portfolios = new ArrayList<>();

}
