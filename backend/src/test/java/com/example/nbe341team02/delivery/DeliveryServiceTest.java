package com.example.nbe341team02.delivery;

import com.example.nbe341team02.domain.delivery.entity.DeliveryTimePolicy;
import com.example.nbe341team02.domain.delivery.repository.DeliveryCompanyRepository;
import com.example.nbe341team02.domain.delivery.repository.DeliveryRepository;
import com.example.nbe341team02.domain.delivery.repository.DeliveryTimePolicyRepository;
import com.example.nbe341team02.domain.delivery.service.DeliveryService;
import com.example.nbe341team02.domain.orders.entity.Order;
import com.example.nbe341team02.domain.orders.repository.OrderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private static final DeliveryTimePolicy deliveryTimePolicy =
            DeliveryTimePolicy.builder()
                    .deliveryTime(LocalTime.of(14, 0))
                    .createdAt(LocalDateTime.now())
                    .build();

    @BeforeAll
    static void setUp() {
        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
    }

    @BeforeEach
    void setUpMocks() {
        when(orderRepository.findByDeliveryIsNullAndCreatedAtIsBetween(any(), any()))
                .thenReturn(orders);

        when(deliveryTimePolicyRepository.findTopByOrderByCreatedAtDesc())
                .thenReturn(Optional.of(deliveryTimePolicy));
    }

    @Test
    @DisplayName("배송 서비스 테스트")
    void testDeliveryService(){
        deliveryService.startDelivery(LocalDateTime.now().minusDays(1), LocalDateTime.now());

        verify(deliveryRepository, times(1))
                .save(any());
    }

    @Test
    @DisplayName("배송 시간 변경 테스트")
    void testChangingDeliveryTime(){
        DeliveryTimePolicy newDeliveryTimePolicy =
                DeliveryTimePolicy.builder()
                        .deliveryTime(LocalTime.of(12, 0))
                        .createdAt(LocalDateTime.now())
                        .build();

        when(deliveryTimePolicyRepository.findTopByOrderByCreatedAtDesc())
                .thenReturn(Optional.of(newDeliveryTimePolicy));
        verify(deliveryRepository, times(2))
                .save(any());
    }
}
