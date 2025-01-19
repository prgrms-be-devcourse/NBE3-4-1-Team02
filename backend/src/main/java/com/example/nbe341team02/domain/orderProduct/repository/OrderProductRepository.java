package com.example.nbe341team02.domain.orderProduct.repository;

import com.example.nbe341team02.domain.orderProduct.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
