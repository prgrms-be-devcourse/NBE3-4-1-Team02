package com.example.nbe341team02.global.initData;

import com.example.nbe341team02.domain.delivery.dto.DeliveryTimePolicyRegisterDto;
import com.example.nbe341team02.domain.delivery.service.DeliveryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("deliveryTimePolicyInitData")
@RequiredArgsConstructor
public class DeliveryTimePolicyInitData {
    private final DeliveryService deliveryService;

    @Value("${default.delivery.hour: 0}")
    private int deliveryTimeHour;

    @Value("${default.delivery.minute: 0}")
    private int deliveryTimeMinute;

    @Value("${default.delivery.second: 0}")
    private int deliveryTimeSecond;

    @PostConstruct
    public void init() {
        deliveryService.registerNewDeliveryTimePolicy(DeliveryTimePolicyRegisterDto
                .builder()
                .hour(deliveryTimeHour)
                .minute(deliveryTimeMinute)
                .second(deliveryTimeSecond)
                .build()
        );
    }
}