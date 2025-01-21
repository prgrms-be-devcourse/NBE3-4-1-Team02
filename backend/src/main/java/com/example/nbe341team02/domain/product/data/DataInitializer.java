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
            product1.setStock(100);
            product1.setStatus(true);
            product1.setDescription("1번 상품에 대한 상세 설명");
            product1.setImageUrl("/static/product1.png");
            productRepository.save(product1);

            Product product2 = new Product();
            product2.setName("2번 상품");
            product2.setPrice(4000);
            product2.setStock(200);
            product2.setStatus(true);
            product2.setDescription("2번 상품에 대한 상세 설명");
            product2.setImageUrl("/static/product2.png");
            productRepository.save(product2);

            Product product3 = new Product();
            product3.setName("3번 상품");
            product3.setPrice(3000);
            product3.setStock(200);
            product3.setStatus(true);
            product3.setDescription("3번 상품에 대한 상세 설명");
            product3.setImageUrl("/static/product3.png");
            productRepository.save(product3);

            Product product4 = new Product();
            product4.setName("4번 상품");
            product4.setPrice(4000);
            product4.setStock(200);
            product4.setStatus(true);
            product4.setDescription("4번 상품에 대한 상세 설명");
            product4.setImageUrl("/static/product4.png");
            productRepository.save(product4);
        }
    }
}