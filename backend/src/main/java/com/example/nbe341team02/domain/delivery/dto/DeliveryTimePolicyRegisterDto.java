package com.example.nbe341team02.domain.delivery.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record DeliveryTimePolicyRegisterDto (
        @Min(0)
        @Max(24)
        int hour,

        @Min(0)
        @Max(60)
        Integer minute
){}
