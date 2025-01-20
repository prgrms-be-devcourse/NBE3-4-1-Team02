package com.example.nbe341team02.product.entity;

import com.example.nbe341team02.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void 상품생성_성공() {
        // given
        String productName = "아메리카노";
        int productPrice = 4500;
        int productStock = 100;
        boolean productStatus = true;

        // when
        Product product = Product.builder()
                .productName(productName)      // product_name이 아닌 productName
                .productPrice(productPrice)    // product_price가 아닌 productPrice
                .productStock(productStock)    // product_stock이 아닌 productStock
                .productStatus(productStatus)  // product_status가 아닌 productStatus
                .build();

        // then
        assertThat(product.getProductName()).isEqualTo(productName);     // getProduct_name이 아님
        assertThat(product.getProductPrice()).isEqualTo(productPrice);   // getProduct_price가 아님
        assertThat(product.getProductStock()).isEqualTo(productStock);   // getProduct_stock이 아님
        assertThat(product.isProductStatus()).isTrue();                  // isProduct_status가 아님
    }

}
