package com.edu.orderServiceApplication.service;

import com.edu.orderServiceApplication.client.PriceClient;
import com.edu.orderServiceApplication.client.WalletClient;
import com.edu.orderServiceApplication.dto.OrderRequest;
import com.edu.orderServiceApplication.model.Order;
import com.edu.orderServiceApplication.model.OrderStatus;
import com.edu.orderServiceApplication.model.OrderType;
import com.edu.orderServiceApplication.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final PriceClient priceClient;
    private final WalletClient walletClient;

    @Transactional
    public Order createOrder(Long userId, OrderRequest request){
        Order order = Order.builder()
                .userId(userId)
                .type(request.getType())
                .amount(request.getAmount())
                .side(request.getSide())
                .symbol(request.getSymbol())
                .status(OrderStatus.CREATED)
                .build();
        if (request.getType() == OrderType.MARKET){
            BigDecimal currentPrice = priceClient.getPriceBySymbol(request.getSymbol().getValue());
            request.setTargetPrice(currentPrice);
            BigDecimal totalPrice = currentPrice.multiply(request.getAmount());
            walletClient.executeTrade(userId,
                    request.getSymbol(),
                    request.getAmount(),
                    totalPrice,
                    request.getSide().name());
            order.setStatus(OrderStatus.FILLED);
            return orderRepository.save(order);
        }else {
            order.setTargetPrice(request.getTargetPrice());
            return orderRepository.save(order);
        }
    }
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }
}
