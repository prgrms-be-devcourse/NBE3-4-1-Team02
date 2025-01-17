package com.example.nbe341team02.product.service;


import com.example.nbe341team02.product.dto.ProductDTO;
import com.example.nbe341team02.product.entity.Product;
import com.example.nbe341team02.product.exception.ProductNotFoundException;
import com.example.nbe341team02.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    //상품 목록 조회
    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();


        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found.");
        }

        List<ProductDTO> productDTOList = new ArrayList<>();
        for( Product product : products){
            ProductDTO productDTO = new ProductDTO(
                    product.getProductId(),
                    product.getProductName(),
                    product.getProductPrice(),
                    product.getProductStock(),
                    product.isProductStatus()
            );
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    //상품 추가 기능
    public ProductDTO addProduct(ProductDTO productDTO) {
        // ProductDTO를 Product 엔티티로 변환
        Product product = new Product(
                null,
                productDTO.getProductName(),
                productDTO.getProductPrice(),
                productDTO.getProductStock(),
                productDTO.isProductStatus(),
                LocalDateTime.now(), // createdAt
                LocalDateTime.now()  // updatedAt
        );


        Product savedProduct = productRepository.save(product);

        // 저장된 상품을 DTO로 변환하여 반환
        return new ProductDTO(
                savedProduct.getProductId(),
                savedProduct.getProductName(),
                savedProduct.getProductPrice(),
                savedProduct.getProductStock(),
                savedProduct.isProductStatus()
        );
    }

}
