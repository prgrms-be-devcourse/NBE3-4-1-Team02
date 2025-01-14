package com.example.nbe341team02.product.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductTest {


    @Test
    void 상품생성_성공(){
        String name ="게이샤";
        int price = 10000;
        String description= "산미";
        int stock = 100;

        Product product = new Product(name, price, description, stock);

        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getDescription()).isEqualTo(description);
        assertThat(product.getStock()).isEqualTo(stock);
    }
}
