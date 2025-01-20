package com.example.nbe341team02.domain.delivery.controller;

import com.example.nbe341team02.domain.delivery.dto.DeliveryTimePolicyRegisterDto;
import com.example.nbe341team02.domain.delivery.scheduler.DeliveryScheduler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryScheduler deliveryScheduler;

    @PostMapping("/admin/policies/delivery-time")
    public ResponseEntity<Void> createNewDeliveryTimePolicy(
            @Valid @RequestBody DeliveryTimePolicyRegisterDto registerDto
            ){
        deliveryScheduler.setDeliveryTime(registerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
