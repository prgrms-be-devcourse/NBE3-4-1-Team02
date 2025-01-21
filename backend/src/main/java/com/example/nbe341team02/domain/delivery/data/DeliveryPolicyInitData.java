package com.example.nbe341team02.domain.delivery.data;

import com.example.nbe341team02.domain.delivery.entity.DeliveryTimePolicy;
import com.example.nbe341team02.domain.delivery.repository.DeliveryTimePolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component("deliveryTimePolicyInitData")
@RequiredArgsConstructor
public class DeliveryPolicyInitData implements CommandLineRunner {
    private final DeliveryTimePolicyRepository deliveryTimePolicyRepository;

    @Value("${default.delivery.hour: 14}")
    private int deliveryTimeHour;

    @Value("${default.delivery.minute: 0}")
    private int deliveryTimeMinute;

    @Value("${default.delivery.second: 0}")
    private int deliveryTimeSecond;

    @Override
    public void run(String... args) throws Exception {
        DeliveryTimePolicy deliveryTimePolicy = DeliveryTimePolicy.builder()
                .deliveryTime(LocalTime.of(deliveryTimeHour, deliveryTimeMinute, deliveryTimeSecond))
                .build();
        deliveryTimePolicyRepository.save(deliveryTimePolicy);
    }
}
