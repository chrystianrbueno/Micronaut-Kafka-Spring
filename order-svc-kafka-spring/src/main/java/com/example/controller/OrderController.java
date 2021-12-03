package com.example.controller;

import com.example.domain.Order;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> newOrder(@RequestBody Order order){
        log.info("[ORDER-CONTROLLER-SPRING] - Requested the creation of a new Order");
        log.info("[ORDER-CONTROLLER-SPRING] - New Order - {} ", order);
        return new ResponseEntity<>(orderService.newOrder(order),HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> listOrders(){
        log.info("[ORDER-CONTROLLER-SPRING] -  Requested the list of Orders on Database");
        return new ResponseEntity<>(orderService.getOrders(), HttpStatus.OK);
    }
}
