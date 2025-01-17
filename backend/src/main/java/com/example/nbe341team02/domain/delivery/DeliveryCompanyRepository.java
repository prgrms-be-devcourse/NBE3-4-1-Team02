package com.example.nbe341team02.domain.delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DeliveryCompanyRepository extends JpaRepository<DeliveryCompany, Long> {
    Set<DeliveryCompany> findByActive(boolean active);
}
