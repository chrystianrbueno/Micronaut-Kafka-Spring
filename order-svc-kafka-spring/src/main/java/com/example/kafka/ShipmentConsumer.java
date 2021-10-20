package com.example.kafka;

import com.example.domain.Shipment;
import com.example.domain.ShipmentStatus;
import com.example.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ShipmentConsumer {

    private final OrderService orderService;

    public ShipmentConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "shipping-topic", groupId = "shipping-svc-kafka", containerFactory = "shipmentConcurrentKafkaListenerContainerFactory")
    public void consume(@Payload Shipment shipment){

        log.info("Shipment return -> {}", shipment);
        var order = orderService.getOrderById(shipment.getOrderId());
        order.setShipmentStatus(ShipmentStatus.SHIPPED);
        orderService.updateOrder(order);
        log.info("Order shipment status updated");

    }
}