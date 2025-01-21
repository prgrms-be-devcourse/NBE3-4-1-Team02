package com.example.nbe341team02.domain.orders.data;

import com.example.nbe341team02.domain.orders.controller.OrderController;
import com.example.nbe341team02.domain.orders.dto.request.OrderCreateRequest;
import com.example.nbe341team02.domain.orders.dto.request.OrderProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderInitData implements CommandLineRunner {

    // 정석적으론 Repository 를 호출해야 적절할 것 같은데 매서드 호출이 복잡해질 것 같아서 일단 그냥 Controller 사용했습니다.
    private final OrderController orderController;
    @Override
    public void run(String... args) throws Exception {
        List<OrderProductRequest> orderProducts = new ArrayList<>();
        for (int i = 1; i < 2; i++) {
            orderProducts.add(OrderProductRequest.builder()
                    .productId((long) i)
                    .quantity(2)
                    .build());
        }
        orderProducts.add(OrderProductRequest.builder()
                .productId(1L)
                .quantity(2)
                .build());
        for (int i = 0; i < 25; i++){
            OrderCreateRequest request = OrderCreateRequest.builder()
                    .email("email" + i + "@email.com")
                    .address("address" + i)
                    .postalCode("12345" + i)
                    .orderProducts(orderProducts)
                    .build();
            orderController.createOrder(request);
        }
    }
}