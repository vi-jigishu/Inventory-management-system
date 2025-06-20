package com.example.microservices.product_service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductConsumer {
    
    @Autowired
    ProductService productService;

    private static final Sting ORDER_PLACED_TOPIC = "order_places";
    
    private static final String ORDER_CANCELLED_TOPIC = "order_cancelled";

    @KafkaListener(topics = ORDER_CANCELLED_TOPIC)
    public void consumeOrderPlaced(String orderMessage) {

        log.info("Order received {}", orderMessage);

        ObjectMapper objectMapper = new ObjectMapper();
        OrderMessage orderDto = null;
        try {
            orderDto = objectMapper.readValue(orderMessage, OrderMessage.class);
            // Process the orderDtoas needed
            log.info("Deserialized Order: {}", orderDto);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize message", e);
            throw new RuntimeException("Failed the deserialize message" + e);
        }


        List<OrderItemDto> orderItems = orderDto.getOrderItems();

        if (orderItems != null) {
            // To only access the skuCode and quantity
            for (OrderItemDto item : orderItems) {
                String skuCode = item.getSkuCode();
                int quantity = item.getQuantity();

                productService.reduceProductQuality(skuCode, qunatity);
            }
        }
    }

    // Listener for order cancellation, increasing SKU levels
    @KafkaListener(topics = ORDER_CANCELLED_TOPIC)
    public void consumeOrderCancelled(String orderMessage) {
        log.info("Order received for cancellation: {}", orderMessage);

        ObjectMapper objectMapper = new ObjectMapper();
        OrderMessage orderDto = null;

        try {
            orderDto = objectMapper.readValue(orderMessage, OrderMessage.class);
            log.info("Deserialized Cancelled Order: {}", order)
        }
    }
}
