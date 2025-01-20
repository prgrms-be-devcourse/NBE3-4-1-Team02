package com.example.nbe341team02.domain.delivery.dto;

import com.example.nbe341team02.domain.orderProduct.entity.OrderProduct;
import lombok.Builder;
import java.util.Set;

@Builder
public record DeliveryTrackingViewDto (
        String email,
        String address,
        String postalCode,
        Set<OrderProduct> orderProducts,
        String deliveryCompanyName,
        String deliveryCompanyAddress,
        String trackingURLTemplate,
        String deliveryTrackingNumber
){
}
