package com.example.nbe341team02.product.service;

import com.example.nbe341team02.product.entity.Product;
import com.example.nbe341team02.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public int getStock(Long productId) {
        try {
            Product product = productRepository.findById(productId).get();
            return product.getProductStock();
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("상품을 찾을 수 없습니다.");
        }
    }

    public void updateStock(Long productId, int newStock) {
        try {
            Product product = productRepository.findById(productId).get();
            product.updateStock(newStock);
            productRepository.save(product);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("상품을 찾을 수 없습니다.");
        }
    }

    public boolean checkAvailability(Long productId, int orderQuantity) {
        try {
            Product product = productRepository.findById(productId).get();
            return product.getProductStock() >= orderQuantity
                    && product.isProductStatus();
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("상품을 찾을 수 없습니다.");
        }
    }
}