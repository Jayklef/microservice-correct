package com.jayklef.orderservice.controller;

import com.jayklef.orderservice.dto.OrderDetails;
import com.jayklef.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place-order")
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderDetails orderDetails){
        orderService.placeOrder(orderDetails);
        return "Order placed successfully";
    }
}
