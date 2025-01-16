package com.example.nbe341team02.delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryTimePolicyRepository extends JpaRepository<DeliveryTimePolicy, Long> {
    Optional<DeliveryTimePolicy> findTopByOrderByCreatedAtDesc();
}
