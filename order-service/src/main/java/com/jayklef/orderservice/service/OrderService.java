package com.jayklef.orderservice.service;

import com.jayklef.orderservice.dto.OrderDetails;
import com.jayklef.orderservice.dto.OrderItemsDto;
import com.jayklef.orderservice.dto.StockResponse;
import com.jayklef.orderservice.model.Order;
import com.jayklef.orderservice.model.OrderItems;
import com.jayklef.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient webClient;

    public void placeOrder(OrderDetails orderDetails){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItems> orderItems = orderDetails.getOrderItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderItemsList(orderItems);

        List<String> skuCodes = order.getOrderItemsList().stream()
                .map(OrderItems::getSkuCode)
                .toList();

        // call stock service and place order if goods is in stock

       StockResponse[] stockResponseArray = webClient.get()
                        .uri("http:localhost:8093/api/stock",
                                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                        .retrieve()
                        .bodyToMono(StockResponse[].class )
                        .block();

        boolean allProductsInStock = Arrays.stream(stockResponseArray)
                .allMatch(StockResponse::isInStock);

       if (allProductsInStock){
           orderRepository.save(order);
       }else
           throw new IllegalArgumentException("Product is not in stock");
    }

    private OrderItems mapToDto(OrderItemsDto orderItemsDto) {
        OrderItems orderItems = new OrderItems();
        orderItems.setPrice(orderItemsDto.getPrice());
        orderItems.setSkuCode(orderItemsDto.getSkuCode());
        orderItems.setQuantity(orderItemsDto.getQuantity());

        return orderItems;
    }
}
