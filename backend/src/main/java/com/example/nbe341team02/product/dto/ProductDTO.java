package com.example.nbe341team02.product.dto;


import com.example.nbe341team02.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private int price;
    private int stock;
    private boolean status;
}