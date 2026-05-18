package com.edu.orderServiceApplication.dto;

import com.edu.orderServiceApplication.model.OrderSide;
import com.edu.orderServiceApplication.model.OrderType;
import com.edu.orderServiceApplication.model.Symbol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class OrderRequest {
    private Symbol symbol;
    private BigDecimal amount;
    private BigDecimal targetPrice;
    private OrderType type;
    private OrderSide side;
}
