package com.example.nbe341team02.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}

