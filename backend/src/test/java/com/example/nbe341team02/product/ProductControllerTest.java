package com.example.nbe341team02.product;

import com.example.nbe341team02.product.controller.ProductController;
import com.example.nbe341team02.product.dto.ProductDTO;
import com.example.nbe341team02.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ProductControllerTest {
    @Autowired
    private ProductController productController;

    @MockBean // ProductService를 모킹
    private ProductService productService;

    @Test
    void testFindAllProducts() {

        ProductDTO product1 = new ProductDTO(1L, "Coffee", 5000, 100, true);
        ProductDTO product2 = new ProductDTO(2L, "Tea", 3000, 150, true);
        List<ProductDTO> products = Arrays.asList(product1, product2);

        when(productService.findAllProducts()).thenReturn(products);

        // Controller 메서드 호출
        List<ProductDTO> response = productController.findAllProducts();


        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0).getProductName()).isEqualTo("Coffee");
        assertThat(response.get(1).getProductName()).isEqualTo("Tea");

        verify(productService, times(1)).findAllProducts();
    }
}