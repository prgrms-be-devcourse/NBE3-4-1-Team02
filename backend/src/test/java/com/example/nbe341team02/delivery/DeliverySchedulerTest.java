package com.example.nbe341team02.delivery;

import com.example.nbe341team02.domain.delivery.scheduler.DeliveryScheduler;
import com.example.nbe341team02.domain.delivery.service.DeliveryService;
import com.example.nbe341team02.domain.delivery.dto.DeliveryTimePolicyRegisterDto;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.*;


import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("dev")
@EnableScheduling
@Transactional
class DeliverySchedulerTest {
    @Autowired
    private DeliveryScheduler deliveryScheduler;

    @MockitoSpyBean
    private DeliveryService deliveryService;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        LocalTime deliveryTime = LocalTime.now().plusSeconds(10);
        registry.add("default.delivery.hour", deliveryTime::getHour); // 동적 값 설정
        registry.add("default.delivery.minute", deliveryTime::getMinute);
        registry.add("default.delivery.second", deliveryTime::getSecond);
    }

    @Order(0)
    @Test
    @DisplayName("배송 스케쥴러 테스트")
    void testDeliveryScheduler() throws InterruptedException {
        Thread.sleep(20000);
        verify(deliveryService, times(1))
                .startDelivery();
    }

    @Order(1)
    @Test
    @DisplayName("배송 스케쥴러 변경 테스트")
    void testChangeDeliveryTime() throws InterruptedException {
        LocalTime oldDeliveryTime = LocalTime.now().minusSeconds(10);
        DeliveryTimePolicyRegisterDto oldRegisterDto = DeliveryTimePolicyRegisterDto.builder()
                .hour(oldDeliveryTime.getHour())
                .minute(oldDeliveryTime.getMinute())
                .build();
        deliveryScheduler.setDeliveryTime(oldRegisterDto);
        Thread.sleep(20000);
        verify(deliveryService, times(0))
                .startDelivery();

        LocalTime newDeliveryTime = LocalTime.now().plusSeconds(10);
        DeliveryTimePolicyRegisterDto registerDto = DeliveryTimePolicyRegisterDto.builder()
                .hour(newDeliveryTime.getHour())
                .minute(newDeliveryTime.getMinute())
                .build();
        deliveryScheduler.setDeliveryTime(registerDto);
        Thread.sleep(15000);
        verify(deliveryService, times(1))
                .registerNewDeliveryTimePolicy(registerDto);
    }
}
