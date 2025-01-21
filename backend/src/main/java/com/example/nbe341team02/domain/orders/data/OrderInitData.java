package com.example.nbe341team02.domain.orders.data;

import com.example.nbe341team02.domain.orders.controller.OrderController;
import com.example.nbe341team02.domain.orders.dto.request.OrderCreateRequest;
import com.example.nbe341team02.domain.orders.dto.request.OrderProductRequest;
import com.example.nbe341team02.domain.product.dto.ProductDTO;
import com.example.nbe341team02.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(2)
public class OrderInitData implements CommandLineRunner {

    // 정석적으론 Repository 를 호출해야 적절할 것 같은데 매서드 호출이 복잡해질 것 같아서 일단 그냥 Controller 사용했습니다.
    private final OrderController orderController;
    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        List<ProductDTO> products = productService.findAllProducts();
        if (products.isEmpty()) {
            log.warn("상품 데이터가 없습니다. 주문 초기화를 건너뜁니다.");
            return;
        }

        List<OrderProductRequest> orderProducts = new ArrayList<>();
        ProductDTO product = products.get(0);
        
        orderProducts.add(OrderProductRequest.builder()
                .productId(product.getId())
                .quantity(2)
                .build());

        for (int i = 0; i < 25; i++) {
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
