package com.example.nbe341team02.domain.product.service;

import com.example.nbe341team02.domain.product.dto.ProductDTO;
import com.example.nbe341team02.domain.product.entity.Product;
import com.example.nbe341team02.domain.product.repository.ProductRepository;
import com.example.nbe341team02.global.exception.CustomException;
import com.example.nbe341team02.global.exception.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            throw new CustomException((ErrorCode.PRODUCT_NOT_FOUND));
        }

        List<ProductDTO> productDTOList = new ArrayList<>();

        for( Product product : products){
            ProductDTO productDTO = new ProductDTO(
              product.getId(),
              product.getName(),
              product.getPrice(),
              product.getStock(),
              product.isStatus()
            );
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    @Transactional
    public Product reduceStock(Long productId, int quantity) {
        Product product = getProduct(productId);
        product.reduceStock(quantity);
        return productRepository.save(product);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
          .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }
}

