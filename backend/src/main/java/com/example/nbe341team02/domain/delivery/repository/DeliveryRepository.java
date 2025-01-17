package com.example.nbe341team02.domain.delivery.repository;

import com.example.nbe341team02.domain.delivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
