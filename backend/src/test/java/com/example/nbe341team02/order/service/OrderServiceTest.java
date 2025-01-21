package com.example.nbe341team02.order.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.nbe341team02.domain.orderProduct.repository.OrderProductRepository;
import com.example.nbe341team02.domain.orders.dto.request.OrderCreateRequest;
import com.example.nbe341team02.domain.orders.dto.request.OrderProductRequest;
import com.example.nbe341team02.domain.orders.entity.Order;
import com.example.nbe341team02.domain.orders.repository.OrderRepository;
import com.example.nbe341team02.domain.orders.service.OrderService;
import com.example.nbe341team02.domain.product.entity.Product;
import com.example.nbe341team02.domain.product.service.ProductService;
import com.example.nbe341team02.global.exception.CustomException;
import com.example.nbe341team02.global.exception.ErrorCode;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("예외 테스트: 상품 재고 부족 ")
    void testCreateOrder_InsufficientStock() {
        // Given
        OrderCreateRequest request = new OrderCreateRequest(
          "test@example.com",
          "Test Address",
          "03344",
          List.of(new OrderProductRequest(1L, 10)) // 초과 수량 요청
        );

        when(productService.reduceStock(1L, 10))
          .thenThrow(new CustomException(ErrorCode.INSUFFICIENT_STOCK));

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            orderService.createOrder(request);
        });

        assertEquals(ErrorCode.INSUFFICIENT_STOCK, exception.getErrorCode());
        verify(orderRepository, never()).save(any(Order.class));
        verify(orderProductRepository, never()).saveAll(anyList());
    }

    @Test
    @DisplayName("예외 테스트: 존재하지 않는 상품 ")
    void testCreateOrder_ProductNotFound() {
        // Given
        OrderCreateRequest request = new OrderCreateRequest(
          "test@example.com",
          "Test Address",
          "03344",
          List.of(new OrderProductRequest(999L, 2))
        );

        when(productService.reduceStock(999L, 2))
          .thenThrow(new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        // When & Then
        CustomException exception = assertThrows(CustomException.class, () -> {
            orderService.createOrder(request);
        });

        assertEquals(ErrorCode.PRODUCT_NOT_FOUND, exception.getErrorCode());
        verify(orderRepository, never()).save(any(Order.class));
        verify(orderProductRepository, never()).saveAll(anyList());
    }

    @Test
    @DisplayName("정상 테스트: 주문 성공")
    public void testCreateOrder() {
        // Given
        Product product = new Product(1L, "Product 1", 1000, 3, true, "설명", "image-url");
        OrderCreateRequest request = new OrderCreateRequest(
          "test@example.com",
          "Test Address",
          "03344",
          List.of(new OrderProductRequest(1L, 2))
        );

        when(productService.reduceStock(1L, 2)).thenReturn(product);

        // When
        orderService.createOrder(request);

        // Then
        verify(orderRepository).save(any(Order.class));
        verify(orderProductRepository).saveAll(anyList());
        verify(productService).reduceStock(1L, 2);
    }

}
