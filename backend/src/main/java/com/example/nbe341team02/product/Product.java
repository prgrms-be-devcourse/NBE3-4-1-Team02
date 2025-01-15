package com.example.nbe341team02.product;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    private String description;

    @Column(nullable = false)
    private int stock;

    public Product(String name, int price, String description, int stock){
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;

    }
}
