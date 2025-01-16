package com.example.nbe341team02.domain.orders.dto.request;//package com.example.nbe341team02.domain.order.dto.request;

import com.example.nbe341team02.domain.orders.entity.Order;
import com.example.nbe341team02.domain.orders.enums.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderCreateRequest {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private final String email;

    @NotBlank(message = "주소는 필수 입력값입니다.")
    @Size(max = 255)
    private final String address;

    @NotEmpty(message = "주문 상품은 최소 1개 이상 선택해야 합니다.")
    @Valid
    private final List<OrderProductRequest> orderProducts;

    public Order toEntity() {
        return Order.builder()
          .email(email)
          .address(address)
          .status(OrderStatus.COMPLETED)
          .build();
    }
}
