package com.example.nbe341team02.domain.delivery.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record DeliveryTimePolicyRegisterDto (
        @Min(0)
        @Max(23)
        int hour,

        @Min(0)
        @Max(59)
        Integer minute,

        @Min(0)
        @Max(59)
        Integer second
){}
