package com.example.nbe341team02.product.service;


import com.example.nbe341team02.product.dto.ProductDTO;
import com.example.nbe341team02.product.entity.Product;
import com.example.nbe341team02.product.exception.ProductNotFoundException;
import com.example.nbe341team02.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

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
}
