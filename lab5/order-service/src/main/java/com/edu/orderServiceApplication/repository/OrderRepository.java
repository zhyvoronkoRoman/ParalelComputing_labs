package com.edu.orderServiceApplication.repository;

import com.edu.orderServiceApplication.model.Order;
import com.edu.orderServiceApplication.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long id);
    List<Order> findAllByStatus(OrderStatus status);
}
