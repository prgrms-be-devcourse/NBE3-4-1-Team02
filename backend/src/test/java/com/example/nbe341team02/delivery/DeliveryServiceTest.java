package com.example.nbe341team02.delivery;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("dev")
public class DeliveryServiceTest {
    @InjectMocks
    private DeliveryService deliveryService;

    @MockitoBean
    private OrderRepository orderRepository;

    @MockitoBean
    private DeliveryRepository deliveryRepository;

    @MockitoBean
    private DeliveryCompanyRepository deliveryCompanyRepository;

    @MockitoBean
    private DeliveryTimePolicyRepository deliveryTimePolicyRepository;

    private static final List<Order> orders = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        Order order = new Order();
        orders.add(order);
    }

    @BeforeEach
    void setUpMocks() {
        when(orderRepository.findByOrderTimeBetween(any(), any()))
                .thenReturn(orders);
    }

    @Test
    @DisplayName("배송 서비스 테스트")
    void testDeliveryScheduler(){
        deliveryService.startDelivery(LocalDateTime.now().minusDays(1), LocalDateTime.now());
        verify(deliveryRepository, times(1))
                .save(any());
    }
}
