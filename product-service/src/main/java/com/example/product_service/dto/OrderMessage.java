package com.example.microservices.product_service.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderMessage {
    
    @JsonProperty("OrderItems")
    private List<OrderItemDto> orderItems;
}
