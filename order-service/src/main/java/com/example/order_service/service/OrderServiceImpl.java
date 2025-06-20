package com.example.microservices.order_service.service;

import com.example.microservices.order_service.*;

import jakarta.transaction.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    OrderProducer OrderProducer;

    @Override
    public List<OrderDto> getAllOrders(Long id){
        return OrderRepository.findAll().stream().map(this::convertToDto). collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDto(order);
    }


    @Override
    @Transactional
    public OrderResponse createOrder(OrderDto orderDto) {
        Order order = convertToEntity(orderDto);

        Order finalOrder = order;
        List<OrderItem> orderItemList = orderDto.getOrderItems().stream()
                .map(itemDto -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem orderItem = new OrderItem();
                    orderItem.setProductId(itemDto.getSkuCode());
                    orderItem.setProductName(itemDto.getProductName());
                    orderItem.setQuantity(itemDto.getQuantity());
                    orderItem.setPrice(itemDto.getPrice());
                    orderItem.setOrder(finalOrder);
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItemList);
        order.setOrderStatus(OrderStatus.ORDER_PLACED.name());
        order = orderRepository.save(order);

        return OrderResponse.builder()
                .orderId(order.getId())
                .status("ORDER PLACED")
                .build();
    }


    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order notfound"));
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setOrderDate(orderDto.getOrderDate());

        order.setOrderItems().clear();
        order.getOrderItems().addAll(orderDto.getOrderItems().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList()));
        
        order = orderRepository.save(order);
        return convertToDto(order);
    }


    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    @Override
    public OrderResponse cancelOrder(Lomg orderId) {
        Order order = orderRepository.findBy(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        if(!order.getorderStatus().euqals("Order_CANCELLED")) {
            order.setOrderStatus("Order Cancelled");
            orderRepository.save(order);
        }


        orderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderId.setTotalPrice(order.getTotalPrice());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setOrderItems(order.getOrderItems().stream()
                .map(item -> new OrderItemDto(item.getProductId(), item.getQuantity()))   //Assuming constructor or converter for OrderItemDto
                .collect(Collectors.toList()));

        orderProducer.sendOrderEvent(orderDto, "cancelled");   // Send cancellation event with full order data
        return new OrderResponse(order);   // Return updated order response
        }

    
    private OrderDto convOrderToDto(order order) {
        return OrderDto.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .orderDate(order.getOrderDate())
                .orderItem(order.getOrderItems().stream().map(this::convertToDo).collect(Collectors.toList()))
                .build();
    }

    private OrderItemDto convertToDo(OrderToDto orderItem) {
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .skuCode(orderItem.getProductId())
                .productName(orderItem.getProductName())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }

    private OrderItemDto convertToDo(OrderItem orderDto) {
        return Order.builder()
                .totalPrice(orderDto.getTotalPrice())
                .orderDate(orderDto.getOrderDate())
                .orderItems(orderDto.getOrderItems().stream().map(this::convertToEntity).collect(Collectors.toList()))
                .build();
    }

    private OrderItem convertToEntity(OrderItemDto orderItemDto) {
        return OrderItem.builder()
                .productId(orderItemDto.getSkuCode())
                .productName(orderItemDto.getProduct())
                .quantity(orderItemDto.getQuantity())
                .price(orderItemDto.getPrice())
                .build();
    }


    /*
     * Chacek if the product is avau=ilable based on the SKU code and availability list.
     * 
     * @Param skuCode    The SKU or the product.
     * @param availability List The list of product availability.
     * @return true if the product is availabile; otherwise false.
     */
    @Override
    public boolean isProductAvailable(String skuCode, List<ProductAvailability> availabilityList) {
        return availabilityList.stream()
        .anyMatch(availabilty -> availability.getSkuCode().equals(skuCode) && availability.isAbvailable() );
    }

}
    


