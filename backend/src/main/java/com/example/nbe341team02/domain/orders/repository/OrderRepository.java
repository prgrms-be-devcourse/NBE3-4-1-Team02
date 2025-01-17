package com.example.nbe341team02.domain.orders.repository;//package com.example.nbe341team02.domain.order.repository;

import com.example.nbe341team02.domain.orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
