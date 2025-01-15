package com.example.nbe341team02.delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DeliveryCompanyRepository extends JpaRepository<DeliveryCompany, Long> {
    Set<DeliveryCompany> findByActiveIs(boolean active);
}
