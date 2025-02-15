package com.example.nbe341team02.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductDescriptionDTO {
    private Long id;
    private String name;
    private int price;
    private int stock;
    private boolean status;
    private String imageUrl;

    private String description;
}
