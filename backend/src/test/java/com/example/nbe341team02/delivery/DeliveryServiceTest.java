package com.example.nbe341team02.delivery;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;


import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("dev")
@EnableScheduling
class DeliveryServiceTest {
    @TestConfiguration
    static class SchedulerTestConfig {
        @Bean
        public Clock clock() {
            return Clock.fixed(Instant.parse("2024-01-15T14:00:00Z"), ZoneId.systemDefault());
        }
    }

    @InjectMocks
    private DeliveryService deliveryService;

    @MockitoBean
    private OrderRepository orderRepository;

    @MockitoBean
    private DeliveryRepository deliveryRepository;

    private static final List<Order> orders = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        Order order = new Order();
        orders.add(order);
    }

    @BeforeEach
    void setUpMocks() {
        when(orderRepository.findOrdersToBeDelivered())
                .thenReturn(orders);
    }

    @Test
    @DisplayName("배송 스케쥴러 테스트")
    private void testDeliveryScheduler(){
        verify(deliveryRepository, times(1)).save(any(Order.class));
    }
}
