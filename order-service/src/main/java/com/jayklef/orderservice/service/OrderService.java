package com.jayklef.orderservice.service;

import com.jayklef.orderservice.dto.OrderDetails;
import com.jayklef.orderservice.dto.OrderItemsDto;
import com.jayklef.orderservice.model.Order;
import com.jayklef.orderservice.model.OrderItems;
import com.jayklef.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderDetails orderDetails){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItems> orderItems = orderDetails.getOrderItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderItemsList(orderItems);

        orderRepository.save(order);
    }

    private OrderItems mapToDto(OrderItemsDto orderItemsDto) {
        OrderItems orderItems = new OrderItems();
        orderItems.setPrice(orderItemsDto.getPrice());
        orderItems.setSkuCode(orderItemsDto.getSkuCode());
        orderItems.setQuantity(orderItemsDto.getQuantity());

        return orderItems;
    }
}
