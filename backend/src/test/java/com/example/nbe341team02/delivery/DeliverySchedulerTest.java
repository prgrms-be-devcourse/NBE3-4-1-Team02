package com.example.nbe341team02.delivery;

import com.example.nbe341team02.domain.delivery.scheduler.DeliveryScheduler;
import com.example.nbe341team02.domain.delivery.service.DeliveryService;
import com.example.nbe341team02.domain.delivery.dto.DeliveryTimePolicyRegisterDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.*;


import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
@Slf4j
@SpringBootTest
@ActiveProfiles("dev")
@EnableScheduling
class DeliverySchedulerTest {
    @Autowired
    private DeliveryScheduler deliveryScheduler;

    @MockitoBean
    private DeliveryService deliveryService;

    @BeforeEach
    void setUp() {
        when(deliveryService.getLatestDeliveryTime())
                .thenReturn(LocalTime.of(14, 0));
    }

    @Test
    @DisplayName("배송 스케쥴러 테스트")
    void testDeliveryScheduler() throws InterruptedException {
        Thread.sleep(10000);
        verify(deliveryService, times(1))
                .startDelivery();
    }

    @Test
    @DisplayName("배송 스케쥴러 변경 테스트")
    void testChangeDeliveryTime(){
        DeliveryTimePolicyRegisterDto registerDto = new DeliveryTimePolicyRegisterDto(12, 0);
        deliveryScheduler.setDeliveryTime(registerDto);
        verify(deliveryService, times(1))
                .registerNewDeliveryTimePolicy(registerDto);
    }
}
