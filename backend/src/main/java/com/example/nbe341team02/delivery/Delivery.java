package com.example.nbe341team02.delivery;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.PERSIST)
    @JoinColumn(updatable = false)
    private Set<Order> orders = new HashSet<>();

    @ManyToOne
    private DeliveryCompany deliveryCompany;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @ColumnDefault("CURRENT TIMESTAMP")
    private LocalDateTime createdAt;
}
