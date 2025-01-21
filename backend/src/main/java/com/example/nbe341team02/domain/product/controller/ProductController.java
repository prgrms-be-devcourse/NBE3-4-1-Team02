package com.example.nbe341team02.domain.product.controller;

import com.example.nbe341team02.domain.product.dto.ProductDTO;
import com.example.nbe341team02.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping("/products")
    public List<ProductDTO> findAllProducts(){
        return productService.findAllProducts();
    }
}
