package com.example.nbe341team02.product;

import com.example.nbe341team02.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void 상품이름이_없으면_에러(){
        String emptyName = "";

        assertThrows(IllegalArgumentException.class, () -> {
            new Product(emptyName, 4500, "음료", 100);
        });
    }

    @Test
    void 가격이_0원미만이면_에러() {
        // given
        int invalidPrice = -1000;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            new Product("아메리카노", invalidPrice, "음료", 100);
        });
    }

    @Test
    void 재고가_0개미만이면_에러() {
        // given
        int invalidStock = -10;

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            new Product("아메리카노", 4500, "음료", invalidStock);
        });
    }
    @Test
    void 상품설명은_null이어도_됨() {
        // given
        String name = "아메리카노";
        int price = 4500;
        int stock = 100;

        // when
        Product product = new Product(name, price, null, stock);

        // then
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getDescription()).isNull();
        assertThat(product.getStock()).isEqualTo(stock);
    }
}
