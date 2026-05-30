package com.edu.cryptoTradingApp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LimitOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
    @Column(name = "target_price")
    private BigDecimal targetPrice;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;
    @Enumerated(EnumType.STRING)
    private OrderType type;
    @Enumerated(EnumType.STRING)
    private Symbol symbol;
}
