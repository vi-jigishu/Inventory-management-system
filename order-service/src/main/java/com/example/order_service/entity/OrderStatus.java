package com.example.ordermanagement.order_service.entity;

public enum OrderStatus {
    ORDER_PLACED,
    ORDER_CANCELED;

    @Override
    public String toString() {
        return "OrderStatus{}";
    }
}
