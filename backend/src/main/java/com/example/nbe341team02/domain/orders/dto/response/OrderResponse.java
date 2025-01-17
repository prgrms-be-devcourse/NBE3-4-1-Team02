package com.example.nbe341team02.domain.orders.dto.response;

import com.example.nbe341team02.domain.orderProduct.entity.OrderProduct;
import com.example.nbe341team02.domain.orders.entity.Order;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private String email;
    private String status;
    private List<OrderProductResponse> orderProducts;

    public static OrderResponse of(Order order, List<OrderProduct> orderProducts) {
        return OrderResponse.builder()
          .orderId(order.getId())
          .email(order.getEmail())
          .status(order.getStatus().getKorean())
          .orderProducts(orderProducts.stream()
            .map(OrderProductResponse::of)
            .collect(Collectors.toList()))
          .build();
    }
}

