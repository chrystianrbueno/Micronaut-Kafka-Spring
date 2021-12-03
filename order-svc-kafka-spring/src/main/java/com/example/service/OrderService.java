package com.example.service;

import com.example.domain.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    @Getter
    private List<Order> orders = new ArrayList<>();
    private final KafkaTemplate<String, Order> kafkaTemplate;

    public Order getOrderById(Long id) {
        return orders.stream().filter(it -> it.getId().equals(id)).findFirst().orElse(null);
    }

    public void updateOrder(Order order) {

        log.info("[ORDER-SERVICE-SPRING] - Requesting update order");
        Order existOrder = getOrderById(order.getId());
        int i = orders.indexOf(existOrder);
        orders.set(i, order);
        log.info("[ORDER-SERVICE-SPRING] - Order with ID {} has been updated", order.getId());

    }

    public Order newOrder(Order order){

        log.info("[ORDER-SERVICE-SPRING] - New order received");
        order.setId((long) orders.size());
        this.orders.add(order);
        log.info("[ORDER-SERVICE-SPRING] - New order added on list orders");
        var response = kafkaTemplate.send("order-topic",order);
        response.addCallback(

                success -> log.info("[ORDER-SERVICE-SPRING] - Order created -> {}", order),
                failure -> log.error("[ORDER-SERVICE-SPRING] - Occurred an error at Order id -> {}", order.getId())

        );
        return order;

    }

}