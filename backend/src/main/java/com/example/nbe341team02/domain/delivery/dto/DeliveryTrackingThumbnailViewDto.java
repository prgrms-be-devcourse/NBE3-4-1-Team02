package com.example.nbe341team02.domain.delivery.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class DeliveryTrackingThumbnailViewDto {
    private final Long orderId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private final LocalDateTime orderTime;
    private final String email;
    private final String address;
    private final String postalCode;
    private final String thumbnailProductName;
    private final Long totalCountOfProductType;
    private final String deliveryCompanyName;
    private final String trackingURLTemplate;
    private final String deliveryTrackingNumber;
}
/*
    DetailViewDto 와 구분한 건 List<OrderProduct> 를 찾아오려면 별도 쿼리 호출이 필요해서,
    페이지 사이즈만큼 N + 1 문제가 발생해서 이렇게 처리했습니다. (한 페이지에 주문이 5개면, 5개의 추가 쿼리가 실행됨.)
    total Price 의 경우에도 같은 이유에서 여기엔 없습니다.
 */

