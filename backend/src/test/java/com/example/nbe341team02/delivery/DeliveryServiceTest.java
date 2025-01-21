package com.example.nbe341team02.delivery;

import com.example.nbe341team02.domain.delivery.entity.DeliveryCompany;
import com.example.nbe341team02.domain.delivery.entity.DeliveryTimePolicy;
import com.example.nbe341team02.domain.delivery.repository.DeliveryCompanyRepository;
import com.example.nbe341team02.domain.delivery.repository.DeliveryRepository;
import com.example.nbe341team02.domain.delivery.repository.DeliveryTimePolicyRepository;
import com.example.nbe341team02.domain.delivery.service.DeliveryService;
import com.example.nbe341team02.domain.orders.entity.Order;
import com.example.nbe341team02.domain.orders.enums.OrderStatus;
import com.example.nbe341team02.domain.orders.repository.OrderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
public class DeliveryServiceTest {
    @InjectMocks
    private DeliveryService deliveryService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private DeliveryCompanyRepository deliveryCompanyRepository;

    @Mock
    private DeliveryTimePolicyRepository deliveryTimePolicyRepository;

    private static final List<Order> orders = new ArrayList<>();

    private static final DeliveryTimePolicy deliveryTimePolicy =
            DeliveryTimePolicy.builder()
                    .deliveryTime(LocalTime.of(14, 0))
                    .createdAt(LocalDateTime.now())
                    .build();

    @BeforeAll
    static void setUp() {
        Order order1 = Order.builder()
                .email("email1")
                .status(OrderStatus.COMPLETED)
                .address("address1")
                .postalCode("12345")
                .build();
        Order order2 = Order.builder()
                .email("email1")
                .status(OrderStatus.COMPLETED)
                .address("address1")
                .postalCode("12345")
                .build();
        Order order3 = Order.builder()
                .email("email1")
                .status(OrderStatus.COMPLETED)
                .address("address1")
                .postalCode("12345")
                .build();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
    }

    @BeforeEach
    void setUpMocks() {
        when(orderRepository.findByDeliveryIsNullAndCreatedAtIsBetweenAndStatus(any(), any(), any()))
                .thenReturn(orders);


        when(deliveryCompanyRepository.findByActive(anyBoolean())).thenReturn(Set.of(DeliveryCompany.builder().build()));
    }

    @Test
    @DisplayName("배송 서비스 테스트 - 구체적 시간 명시")
    void testDeliveryService1(){
        deliveryService.startDelivery(LocalDateTime.now().minusDays(1), LocalDateTime.now());

        verify(deliveryRepository, times(1))
                .saveAndFlush(any());
    }

    @Test
    @DisplayName("배송 서비스 테스트 - 구체적 시간 명시 X")
    void testDeliveryService2(){
        when(deliveryTimePolicyRepository.findTopByOrderByCreatedAtDesc())
                .thenReturn(Optional.of(deliveryTimePolicy));

        deliveryService.startDelivery();

        verify(deliveryRepository, times(1))
                .saveAndFlush(any());
    }

    //이거 수정해야 - any, any 말고 다른 조건으로 걸어서 테스트 해야 할 듯.
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

        deliveryService.startDelivery();

        verify(deliveryRepository, times(1))
                .saveAndFlush(any());
    }
}
