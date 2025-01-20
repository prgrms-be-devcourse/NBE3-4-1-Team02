package com.example.nbe341team02.domain.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class DeliveryTrackingThumbnailViewDto {
    private final Long orderId;
    private final String email;
    private final String address;
    private final String postalCode;
    private final String thumbnailProductName;
    private final Long totalCountOfProductType;
    private final String deliveryCompanyName;
    private final String trackingURLTemplate;
    private final String deliveryTrackingNumber;
}
