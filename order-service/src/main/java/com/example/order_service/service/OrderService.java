package com.example.microservices.order_service.service;

import java.util.List;

import com.example.microservices.order_service.dto.OrderDto;
import com.example.*;

public interface OrderService {
    List<OrderDto> getAllOrders();
    OrderDto getOrderById(Long id);
    OrderResponse createOrder(OrderDto OrderDto);
    OrderDto updateOrder(Long id, OrderDto orderDto);
    void deleteOrder(Long id);


    OrderResponse cancelOrder(Long id);

    boolean isProductAvailable(String skuCode, List<ProductAvailability> availabilityList);
}