package com.example.controller;

import com.example.domain.Order;
import com.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<Order> newOrder(@RequestBody Order order){
        return new ResponseEntity<>(orderService.newOrder(order),HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Order>> listOrders(){
        return new ResponseEntity<>(orderService.listOrders(), HttpStatus.OK);
    }
}
