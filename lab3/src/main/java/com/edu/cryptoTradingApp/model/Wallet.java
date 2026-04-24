package com.edu.cryptoTradingApp.model;

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
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;
    @Column(name = "balance_usd")
    private BigDecimal balanceUsd;
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    List<Portfolio> portfolios = new ArrayList<>();

}
