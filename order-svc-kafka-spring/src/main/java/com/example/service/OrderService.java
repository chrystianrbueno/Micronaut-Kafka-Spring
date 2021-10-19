package com.example.service;

import com.example.domain.Order;
import com.example.domain.Shipment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    public List<Order> orders = new ArrayList<>();

    public final KafkaTemplate<String, Order> kafkaTemplate;

    public OrderService(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<Order> listOrders(){
        return orders;
    }

    public Order newOrder(Order order){
        log.info("New order received");
        order.setId((long) orders.size());
        this.orders.add(order);
        var response = kafkaTemplate.send("order-topic",order);
        response.addCallback(
                success -> log.info("[Order-Topic] - Envio com sucesso no order de id -> {}", order.getId()),
                failure -> log.error("[Order-Topic] - Ocorreu um erro no order de id -> {}", order.getId())
        );
        return order;
    }

}