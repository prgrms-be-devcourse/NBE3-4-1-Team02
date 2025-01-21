package com.example.nbe341team02.domain.delivery.repository;

import com.example.nbe341team02.domain.delivery.entity.DeliveryTimePolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryTimePolicyRepository extends JpaRepository<DeliveryTimePolicy, Long> {
    Optional<DeliveryTimePolicy> findTopByOrderByCreatedAtDesc();
    Page<DeliveryTimePolicy> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
