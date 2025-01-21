package com.example.nbe341team02.domain.delivery.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;

import com.example.nbe341team02.domain.delivery.entity.DeliveryCompany;
import com.example.nbe341team02.domain.delivery.repository.DeliveryCompanyRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeliveryCompanyInitData implements CommandLineRunner {
    private final DeliveryCompanyRepository deliveryCompanyRepository;

    @Override
    public void run(String... args) throws Exception {
            deliveryCompanyRepository.save(DeliveryCompany.builder()
                    .companyName("한진택배")
                    .trackingURLTemplate("https://www.hanjin.com/kor/CMS/DeliveryMgr/WaybillSch.do?mCode=MN038")
                    .build());
    }
}
