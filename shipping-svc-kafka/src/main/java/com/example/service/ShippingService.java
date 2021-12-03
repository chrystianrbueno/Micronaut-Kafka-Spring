package com.example.service;

import com.example.domain.Order;
import com.example.domain.Shipment;
//import com.example.messaging.ShipmentProducer;
import com.example.messaging.ShipmentProducer;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class ShippingService {

    @Getter
    private final List<Shipment> shipments = Collections.synchronizedList(new ArrayList<>());

    private final ShipmentProducer shipmentProducer;

    public Shipment getShipmentById(Long id) {
        log.info("[SHIPMENT-SERVICE-MICRONAUT] - Finding shipment by id -> {}!", id);
        Shipment shipment;
        synchronized (shipments) {
            shipment = shipments.stream().filter(it -> it.getId().equals(id)).findFirst().orElse(null);
        }
        log.info("[SHIPMENT-SERVICE-MICRONAUT] - Returning shipment -> {}!", shipment);
        return shipment;
    }

    public void updateShipment(Shipment shipment) {
        log.info("[SHIPMENT-SERVICE-MICRONAUT] - Updating shipment -> {}!", shipment);
        Shipment existingShipment = getShipmentById(shipment.getId());
        synchronized (shipments) {
            int i = shipments.indexOf(existingShipment);
            shipments.set(i, shipment);
            log.info("[SHIPMENT-SERVICE-MICRONAUT] - Shipment updated -> {}!", shipment);
        }
    }

    public Shipment newShipment(Order order) {
        var shipment = Shipment.builder()
                                    .id((long) shipments.size())
                                    .orderId(order.getId())
                                    .shippedOn(new Date())
                                    .build();
        log.info("[SHIPMENT-SERVICE-MICRONAUT] - Shipment created!");
        synchronized (shipments) {
            shipments.add(shipment);
        }
        log.info("[SHIPMENT-SERVICE-MICRONAUT] - Shipment added a list of Shipments!");
        log.info("[SHIPMENT-SERVICE-MICRONAUT] - Shipment message - Producing.... ");
        shipmentProducer.sendMessage(shipment);
        log.info("[SHIPMENT-SERVICE-MICRONAUT] - Shipment message sent!");
        return shipment;
    }
}
