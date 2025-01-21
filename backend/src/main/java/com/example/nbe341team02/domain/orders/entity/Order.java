package com.example.nbe341team02.domain.orders.entity;

import com.example.nbe341team02.domain.delivery.entity.Delivery;
import com.example.nbe341team02.global.common.BaseTimeEntity;
import com.example.nbe341team02.domain.orders.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "orders")
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Delivery delivery;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @Column(name = "postal_code", nullable = false, length = 20)
    private String postalCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private OrderStatus status;

    @Builder
    public Order(Long id, String email, String address, String postalCode, OrderStatus status) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
        this.status = status;
    }
}

