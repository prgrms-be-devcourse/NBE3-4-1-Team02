package com.example.nbe341team02.domain.delivery.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nbe341team02.domain.delivery.entity.DeliveryCompany;

public interface DeliveryCompanyRepository extends JpaRepository<DeliveryCompany, Long> {
    Set<DeliveryCompany> findByActive(boolean active);
}
