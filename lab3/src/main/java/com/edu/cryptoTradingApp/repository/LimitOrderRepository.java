package com.edu.cryptoTradingApp.repository;

import com.edu.cryptoTradingApp.model.LimitOrder;
import com.edu.cryptoTradingApp.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LimitOrderRepository extends JpaRepository<LimitOrder, Long> {
    List<LimitOrder> findAllByStatus(Status status);

}
