package com.example.nbe341team02.domain.delivery.controller;

import com.example.nbe341team02.domain.delivery.dto.DeliveryTimePolicyRegisterDto;
import com.example.nbe341team02.domain.delivery.dto.DeliveryTrackingDetailViewDto;
import com.example.nbe341team02.domain.delivery.dto.DeliveryTrackingThumbnailViewDto;
import com.example.nbe341team02.domain.delivery.scheduler.DeliveryScheduler;
import com.example.nbe341team02.domain.delivery.service.DeliveryService;
import com.example.nbe341team02.global.page.PageRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryScheduler deliveryScheduler;
    private final DeliveryService deliveryService;

    @PostMapping("/admin/policies/delivery-time")
    public ResponseEntity<Void> createNewDeliveryTimePolicy(
            @Valid @RequestBody DeliveryTimePolicyRegisterDto registerDto
            ){
        deliveryScheduler.setDeliveryTime(registerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/admin/deliveries")
    public ResponseEntity<Page<DeliveryTrackingThumbnailViewDto>> getAllDeliveryTrackingPage(
            @Valid @ModelAttribute PageRequestDto requestDto,
            @RequestParam(required = false) String email
            ){
        return new ResponseEntity<>(deliveryService.getDeliveryTrackingPage(requestDto, email), HttpStatus.OK);
    }

    @GetMapping("/admin/deliveries/{orderId}")
    public ResponseEntity<DeliveryTrackingDetailViewDto> getDeliveryTrackingDetail(
            @PathVariable Long orderId) {
        return new ResponseEntity<>(deliveryService.getDeliveryTrackingDetail(orderId), HttpStatus.OK);
    }
}
