package com.example.nbe341team02.global.page;

import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record PageRequestDto (
        @Min(0)
        Integer page,

        @Min(1)
        Integer size
){
}
