package com.example.ordermanagement.order_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private BigDecimal totalPrice;

    @JasonFormat(pattern = "yyyy-MM-dd'T':HH:mm:ss")
    private LocalDateTime orderDate;

    private List<OrderItemDto> orderItems;
}
