package com.example.nbe341team02.domain.orders.enums;

public enum OrderStatus {
    COMPLETED("주문 완료"),
    CANCELLED("주문 취소");

    private final String korean;

    OrderStatus(String korean) {
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }
}