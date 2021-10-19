package com.example.domain;

import lombok.Data;

@Data
public class Order {
    private Long id;
    private Integer customerId;
    private Double totalCost;
    private ShipmentStatus shipmentStatus;

    public Order(Long id, Integer customerId, Double totalCost, ShipmentStatus shipmentStatus) {
        this.id = id;
        this.customerId = customerId;
        this.totalCost = totalCost;
        this.shipmentStatus = shipmentStatus != null ? shipmentStatus: ShipmentStatus.PENDING;
    }
}
