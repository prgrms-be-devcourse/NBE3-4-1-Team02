package com.example.nbe341team02.domain.product.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductRequestDTO {
    private String name;
    private int price;
    private int stock;
    private boolean status;
    private String description;
    private MultipartFile file;
}