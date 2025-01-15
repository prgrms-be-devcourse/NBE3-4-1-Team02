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


    //Test 통과용 코드들
    public Product(String name, int price, String description, int stock) {
        validateName(name);// 이름 검증 로직 추가
        validatePrice(price);
        validateStock(stock);


        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("상품 이름은 필수입니다.");
        }
    }
    private void validatePrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("상품 가격은 0원 이상입니다.");
        }
    }
    private void validateStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("재고는 0개 이상이어야 합니다");
        }
    }
}
