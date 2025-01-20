package com.example.nbe341team02.product.data;

import com.example.nbe341team02.product.entity.Product;
import com.example.nbe341team02.product.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            productRepository.save(new Product(
                    null,
                    "1번 상품",
                    5000,
                    10,
                    true,
                    LocalDateTime.now(), // createdAt
                    LocalDateTime.now()  // updatedAt
            ));
            productRepository.save(new Product(
                    null,
                    "2번 상품",
                    4000,
                    20,
                    true,
                    LocalDateTime.now(), // createdAt
                    LocalDateTime.now()  // updatedAt
            ));
        }
    }
}