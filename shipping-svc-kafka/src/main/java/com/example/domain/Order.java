package com.example.domain;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Introspected
@Data
@Builder
public class Order {
    private Long id;
    private Integer customerId;
    private Double totalCost;
    private ShipmentStatus shipmentStatus;
}
