package com.example.nbe341team02.domain.delivery.dto;

import com.example.nbe341team02.domain.orderProduct.entity.OrderProduct;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
public class DeliveryTrackingDetailViewDto {
    private final Long orderId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime orderTime;
    private final String email;
    private final String address;
    private final String postalCode;
    @Setter
    private Long totalPrice;
    @Setter
    private Set<OrderProduct> orderProducts;
    private final String deliveryCompanyName;
    private final String trackingURLTemplate;
    private final String deliveryTrackingNumber;

    //Querydsl 의 Projections 용으로 만든 생성자입니다. 밑의 생성자 없으면 개별 배송 조회 시 예외 발생합니다.
    public DeliveryTrackingDetailViewDto(Long orderId, LocalDateTime orderTime, String email, String address, String postalCode, String deliveryCompanyName, String trackingURLTemplate, String deliveryTrackingNumber) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.email = email;
        this.address = address;
        this.postalCode = postalCode;
        this.deliveryCompanyName = deliveryCompanyName;
        this.trackingURLTemplate = trackingURLTemplate;
        this.deliveryTrackingNumber = deliveryTrackingNumber;
    }
}
