package com.example.nbe341team02.domain.orders.dto.response;

import com.example.nbe341team02.domain.orderProduct.entity.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderProductResponse {
    private String productName;
    private long price;
    private int quantity;

    public static OrderProductResponse of(OrderProduct orderProduct) {
        return OrderProductResponse.builder()
          .productName(orderProduct.getProduct().getName())
          .price(orderProduct.getPrice())
          .quantity(orderProduct.getQuantity())
          .build();
    }
}
