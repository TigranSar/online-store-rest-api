package com.online.store.controller;

import com.online.store.dto.order.OrderResponseDto;
import com.online.store.dto.order.OrderRequestDto;
import com.online.store.service.order.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderRequestDto));
    }


//    @GetMapping
//    public List<OrderDto> getOrders(@RequestParam(value = "status", required = false) String status) {
//        if (status == null) {
//            return orderService.getAllOrders(); // все заказы
//        } else {
//            return orderService.getOrdersByStatus(OrderStatus.valueOf(status.toUpperCase()));
//        }
//    }
}
