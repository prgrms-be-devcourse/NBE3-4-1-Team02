package com.example.nbe341team02.global.initData.delivery;

import com.example.nbe341team02.domain.delivery.entity.DeliveryCompany;
import com.example.nbe341team02.domain.delivery.repository.DeliveryCompanyRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryCompanyInitData {
    private final DeliveryCompanyRepository deliveryCompanyRepository;

    @PostConstruct
    public void init() {
        deliveryCompanyRepository.save(DeliveryCompany.builder()
                .companyName("한진택배")
                .trackingURLTemplate("https://www.google.com/?q={}")
                .build());
    }
}
