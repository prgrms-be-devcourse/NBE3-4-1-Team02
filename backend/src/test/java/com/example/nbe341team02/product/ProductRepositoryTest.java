package com.example.nbe341team02.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void 상품저장_성공(){
        //given
        Product product = new Product("에티오피아",10000,"원두",100);
        //when
        Product savedProduct = this.productRepository.save(product);
        //then
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("에티오피아");
        assertThat(savedProduct.getPrice()).isEqualTo(10000);
        assertThat(savedProduct.getDescription()).isEqualTo("원두");
        assertThat(savedProduct.getStock()).isEqualTo(100);
    }
}
