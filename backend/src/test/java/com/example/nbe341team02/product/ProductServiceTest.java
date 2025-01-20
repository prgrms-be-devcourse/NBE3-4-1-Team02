package com.example.nbe341team02.product;

import com.example.nbe341team02.product.entity.Product;
import com.example.nbe341team02.product.repository.ProductRepository;
import com.example.nbe341team02.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void 재고조회_성공() {
        // given
        Long productId = 1L;
        Product product = Product.builder()
                .productId(productId)
                .productStock(100)
                .build();
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        // when
        int stock = productService.getStock(productId);

        // then
        assertThat(stock).isEqualTo(100);
        verify(productRepository).findById(productId);
    }

    @Test
    void 재고변경_성공() {
        // given
        Long productId = 1L;
        Product product = Product.builder()
                .productId(productId)
                .productStock(100)
                .build();
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        // when
        productService.updateStock(productId, 50);

        // then
        assertThat(product.getProductStock()).isEqualTo(50);
        verify(productRepository).save(product);
    }

    @Test
    void 주문가능여부_확인_성공() {
        // given
        Long productId = 1L;
        int orderQuantity = 50;
        Product product = Product.builder()
                .productId(productId)
                .productStock(100)
                .productStatus(true)
                .build();
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        // when
        boolean isAvailable = productService.checkAvailability(productId, orderQuantity);

        // then
        assertThat(isAvailable).isTrue();
    }

    @Test
    void 재고부족시_주문불가() {
        // given
        Long productId = 1L;
        int orderQuantity = 150;
        Product product = Product.builder()
                .productId(productId)
                .productStock(100)
                .productStatus(true)
                .build();
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        // when
        boolean isAvailable = productService.checkAvailability(productId, orderQuantity);

        // then
        assertThat(isAvailable).isFalse();
    }
}