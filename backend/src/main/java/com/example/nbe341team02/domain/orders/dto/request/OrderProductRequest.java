package com.example.nbe341team02.domain.orders.dto.request;//package com.example.nbe341team02.domain.order.dto.request;

import com.example.nbe341team02.domain.orderProduct.entity.OrderProduct;
import com.example.nbe341team02.domain.orders.entity.Order;
import com.example.nbe341team02.domain.product.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderProductRequest {

    @NotNull(message = "상품 ID는 필수 입력값입니다.")
    private Long productId;

    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private int quantity;

    public OrderProduct toEntity(Order order, Product product) {
        return OrderProduct.builder()
          .order(order)
          .product(product)
          .quantity(quantity)
          .price(product.getPrice())
          .build();
    }
}
