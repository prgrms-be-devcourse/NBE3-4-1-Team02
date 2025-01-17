package com.example.nbe341team02.product.controller;

import com.example.nbe341team02.product.dto.ProductDTO;
import com.example.nbe341team02.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // 여기에 추가

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    @GetMapping
    public List<ProductDTO> findAllProducts(){
        return productService.findAllProducts();
    }
}
