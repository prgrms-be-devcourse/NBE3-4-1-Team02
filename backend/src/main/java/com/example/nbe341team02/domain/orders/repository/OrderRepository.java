package com.example.nbe341team02.domain.orders.repository;//package com.example.nbe341team02.domain.order.repository;

import com.example.nbe341team02.domain.orders.entity.Order;
import com.example.nbe341team02.domain.orders.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByDeliveryIsNullAndCreatedAtIsBetweenAndStatus(LocalDateTime start, LocalDateTime end, OrderStatus status);
}
