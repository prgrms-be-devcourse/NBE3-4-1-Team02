package com.example.nbe341team02.domain.product.data;

import com.example.nbe341team02.domain.product.entity.Product;
import com.example.nbe341team02.domain.product.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            Product product1 = new Product();
            product1.setName("1번 상품");
            product1.setPrice(5000);
            product1.setStock(10);
            product1.setStatus(true);
            productRepository.save(product1);

            Product product2 = new Product();
            product2.setName("2번 상품");
            product2.setPrice(4000);
            product2.setStock(20);
            product2.setStatus(true);
            productRepository.save(product2);

            Product product3 = new Product();
            product3.setName("3번 상품");
            product3.setPrice(3000);
            product3.setStock(20);
            product3.setStatus(true);
            productRepository.save(product3);

            Product product4 = new Product();
            product4.setName("4번 상품");
            product4.setPrice(4000);
            product4.setStock(20);
            product4.setStatus(true);
            productRepository.save(product4);
        }
    }
}