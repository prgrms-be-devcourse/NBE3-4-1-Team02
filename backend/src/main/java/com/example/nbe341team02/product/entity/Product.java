//package com.example.nbe341team02.product.entity;
//
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Table(name = "product")
//public class Product {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private int price;
//
//    @Column(nullable = false)
//    private int stock;
//
//    @Column(nullable = false)
//    private boolean status;
//
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime createdAt;
//
//    @Column(nullable = false)
//    private LocalDateTime updatedAt;
//
//    @PrePersist
//    protected void onCreate() {
//        this.createdAt = LocalDateTime.now();
//        this.updatedAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        this.updatedAt = LocalDateTime.now();
//    }
//}