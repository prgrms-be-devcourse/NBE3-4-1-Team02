package com.example.nbe341team02.domain.delivery.entity;

import jakarta.persistence.*;

@Entity
public class DeliveryCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(20)")
    private String companyName;

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String trackingURLTemplate;

    @Column(nullable = false)
    private boolean active = true;
}
