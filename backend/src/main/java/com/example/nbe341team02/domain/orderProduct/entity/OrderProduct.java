package com.example.nbe341team02.domain.orderProduct.entity;

import com.example.nbe341team02.domain.orders.entity.Order;
import com.example.nbe341team02.domain.product.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity(name = "orders_products")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @JsonIgnore // 배송 조회 Dto 에 이미 주문 관련 정보 (이메일 등)가 있어서 직렬화에서 배제했습니다.
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER) // 직렬화할 때 프록시 객체 상태면 역직렬화 실패해서 EAGER 로 변경했습니다.
    @JoinColumn(nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 19, scale = 2)
    private long price;

    @Builder
    public OrderProduct(Long id, Order order, Product product, int quantity, long price) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        }
    }
    