package com.example.nbe341team02;

import com.example.nbe341team02.product.entity.Product;
import com.example.nbe341team02.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Nbe341Team02ApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        // 초기 데이터를 DB에 삽입
        if (productRepository.count() == 0) {
            productRepository.save(Product.builder()
                    .productName("Coffee")
                    .productPrice(1000)
                    .productStock(50)
                    .productStatus(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build());

            productRepository.save(Product.builder()
                    .productName("Tea")
                    .productPrice(1500)
                    .productStock(30)
                    .productStatus(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build());

            productRepository.save(Product.builder()
                    .productName("Juice")
                    .productPrice(2000)
                    .productStock(20)
                    .productStatus(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build());
        }
    }

    @Test
    void testFindAllProducts() {
        // 예시로 findAllProducts 메서드 테스트
        assertNotNull(productRepository.findAll());
        assertEquals(3, productRepository.findAll().size());  // 초기 데이터가 3개여야 함
    }
}