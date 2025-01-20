package com.example.nbe341team02.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomErrorResponse {

    private String httpStatus;
    private String errorCode;
    private String errorMessage;
}