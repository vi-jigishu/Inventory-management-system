package com.example.microservices.order_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {
    
    private final OrderService orderService;
    private final ProductServiceClient ProductServiceClient;
    private final OrderProducer OrderProducer;

    public OrderController(OrderService orderService, ProductServiceClient productServiceClient, OrderProducer orderProducer) {
        this.orderService = orderService;
        this.ProductServiceClient = productServiceClient;
        this.OrderProducer = orderProducer;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderDto orderDto) {
        try {
            // Filter available items by chcecking with ProductService
            Lsit<ProductAvailability> availabilityList = productServiceClient.checkProductAvailability(orderDto.getOrderItems());

            // Filter available items based on teh availability response
            List<OrderItemDto> availableItems = orderDto.getOrderItems().stream()
                    .filter(item -> orderService.isProductAvailable(item.getSkuCode(), availablityList))
                    .collect(Collectors.toList());

            
            // Handle case where no items are available
            if (availableItems.isEmpty()) {
                log.warn("No items available in teh order request.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new OrderResponse("No items available for the order."));
            } 

            // Update the order with availabile items only
            orderDto.setOrderItems(availableItems);

            // Save the order in the database
            OrderResponse OrderResponse = orderService.createOrder(orderDto);

            if(orderResponse != null) {
                log.info("Order placed successfully, sending 'Order Placed' event to kafka...");
                orderDto.setId(orderResponse.getOrderId());
                orderProcedure.sendOrderEvent(orderDto, "placed");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
        } catch (Exception e) {
            log.error("Error while creating order", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create order");
        }
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id) {
        try {
            OrderResponse orderResponse = orderService.cancelOrder(id);
            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            log.error("Error while cancelli order", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to cancle order");
        }
    }


}
