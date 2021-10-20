package com.example.service;

import com.example.domain.Order;
import com.example.domain.Shipment;
//import com.example.messaging.ShipmentProducer;
import com.example.messaging.ShipmentProducer;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Singleton
@Slf4j
public class ShippingService {

    private final List<Shipment> shipments = Collections.synchronizedList(new ArrayList<>());
    private final ShipmentProducer shipmentProducer;

    public ShippingService(ShipmentProducer shipmentProducer) {
        this.shipmentProducer = shipmentProducer;
    }

    public Shipment getShipmentById(Long id) {
        Shipment shipment;
        synchronized (shipments) {
            shipment = shipments.stream().filter(it -> it.getId().equals(id)).findFirst().orElse(null);
        }
        return shipment;
    }

    public List<Shipment> listShipments() {
        return shipments;
    }

    public void updateShipment(Shipment shipment) {
        Shipment existingShipment = getShipmentById(shipment.getId());
        synchronized (shipments) {
            int i = shipments.indexOf(existingShipment);
            shipments.set(i, shipment);
        }
    }

    public Shipment newShipment(Order order) {
        var shipment = Shipment.builder()
                                    .id((long) shipments.size())
                                    .orderId(order.getId())
                                    .shippedOn(new Date())
                                    .build();
        synchronized (shipments) {
            shipments.add(shipment);
        }
        log.info("Shipment created!");
        log.info("Sending shipment message...");
        shipmentProducer.sendMessage(shipment);
        log.info("Shipment message sent!");
        return shipment;
    }
}
