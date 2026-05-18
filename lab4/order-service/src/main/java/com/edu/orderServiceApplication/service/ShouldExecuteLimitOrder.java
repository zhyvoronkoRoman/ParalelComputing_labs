package com.edu.orderServiceApplication.service;

import com.edu.orderServiceApplication.client.PriceClient;
import com.edu.orderServiceApplication.client.WalletClient;
import com.edu.orderServiceApplication.model.Order;
import com.edu.orderServiceApplication.model.OrderSide;
import com.edu.orderServiceApplication.model.OrderStatus;
import com.edu.orderServiceApplication.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ShouldExecuteLimitOrder {
    private final PriceClient priceClient;
    private final WalletClient walletClient;
    private final OrderRepository orderRepository;

    public boolean shouldExecuteOrder(Order order, BigDecimal currentPrice){
        int condition = currentPrice.compareTo(order.getTargetPrice());
        if(order.getSide() == OrderSide.BUY){
            return condition <=0 ;
        }
        if (order.getSide() == OrderSide.SELL){
            return condition >= 0;
        }
        return false;
    }

    @Scheduled(fixedRate = 10000)
    public void checkLimitOrder(){
        List<Order> openOrders = orderRepository.findAllByStatus(OrderStatus.CREATED);
        BigDecimal currentPrice;
        BigDecimal totalPrice;
        for (Order o : openOrders) {
            currentPrice = priceClient.getPriceBySymbol(o.getSymbol().getValue());
            totalPrice = currentPrice.multiply(o.getAmount());
            if (shouldExecuteOrder(o, currentPrice)) {
                try {
                    walletClient.executeTrade(o.getUserId(), o.getSymbol(), o.getAmount(), totalPrice, o.getSide().name());
                    o.setStatus(OrderStatus.FILLED);
                }catch (Exception e){
                    System.err.println("Order"+o.getId()+" denied: "+e.getMessage());
                    o.setStatus(OrderStatus.CANCELLED);
                }
                orderRepository.save(o);
            }
        }
    }
}
