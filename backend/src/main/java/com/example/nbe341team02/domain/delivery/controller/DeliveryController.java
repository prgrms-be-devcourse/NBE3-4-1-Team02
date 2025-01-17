package com.example.nbe341team02.domain.delivery.controller;

import com.example.nbe341team02.domain.delivery.scheduler.DeliveryScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryScheduler deliveryScheduler;
}
