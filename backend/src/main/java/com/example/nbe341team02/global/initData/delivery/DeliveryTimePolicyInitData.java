package com.example.nbe341team02.global.initData.delivery;

import com.example.nbe341team02.domain.delivery.entity.DeliveryTimePolicy;
import com.example.nbe341team02.domain.delivery.repository.DeliveryTimePolicyRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component("deliveryTimePolicyInitData")
@RequiredArgsConstructor
public class DeliveryTimePolicyInitData {
    private final DeliveryTimePolicyRepository deliveryTimePolicyRepository;

    @Value("${default.delivery.hour: 0}")
    private int deliveryTimeHour;

    @Value("${default.delivery.minute: 0}")
    private int deliveryTimeMinute;

    @Value("${default.delivery.second: 0}")
    private int deliveryTimeSecond;

    @PostConstruct
    public void init() {
        DeliveryTimePolicy deliveryTimePolicy = DeliveryTimePolicy.builder()
                .deliveryTime(LocalTime.of(deliveryTimeHour, deliveryTimeMinute, deliveryTimeSecond))
                .build();
        deliveryTimePolicyRepository.save(deliveryTimePolicy);
    }
}