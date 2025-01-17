package com.example.nbe341team02.domain.orders.service;//package com.example.nbe341team02.domain.order.service;

import com.example.nbe341team02.domain.orderProduct.entity.OrderProduct;
import com.example.nbe341team02.domain.orderProduct.repository.OrderProductRepository;
import com.example.nbe341team02.domain.orders.dto.request.OrderCreateRequest;
import com.example.nbe341team02.domain.orders.dto.request.OrderProductRequest;
import com.example.nbe341team02.domain.orders.dto.response.OrderResponse;
import com.example.nbe341team02.domain.orders.entity.Order;
import com.example.nbe341team02.domain.orders.repository.OrderRepository;
import com.example.nbe341team02.domain.product.entity.Product;
import com.example.nbe341team02.domain.product.repository.ProductRepository;
import com.example.nbe341team02.domain.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductService productService;

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {
        Order order = request.toEntity();

        List<OrderProduct> orderProducts = request.getOrderProducts().stream()
          .map(productRequest -> {
              Product product = productService.reduceStock(productRequest.getProductId(), productRequest.getQuantity());
              return productRequest.toEntity(order, product);
          })
          .toList();

        orderRepository.save(order);
        orderProductRepository.saveAll(orderProducts);

        return OrderResponse.of(order,orderProducts);
    }
}

