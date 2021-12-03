package com.example.kafka;

import com.example.domain.Shipment;
import com.example.domain.ShipmentStatus;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ShipmentConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "shipping-topic", groupId = "shipping-svc-kafka", containerFactory = "shipmentConcurrentKafkaListenerContainerFactory")
    public void consume(@Payload Shipment shipment){

        log.info("[SHIPMENT-CONSUMER-SPRING] - Shipment returned -> {}", shipment);
        var order = orderService.getOrderById(shipment.getOrderId());
        log.info("[SHIPMENT-CONSUMER-SPRING] - Changing status on Order id -> {}. Current status is {}", order.getId(), order.getShipmentStatus());
        order.setShipmentStatus(ShipmentStatus.SHIPPED);
        log.info("[SHIPMENT-CONSUMER-SPRING] - Status changed on Order id -> {}. Current status now is {}", order.getId(), order.getShipmentStatus());
        orderService.updateOrder(order);
        log.info("[SHIPMENT-CONSUMER-SPRING] - Order shipment updated on Database");

    }
}