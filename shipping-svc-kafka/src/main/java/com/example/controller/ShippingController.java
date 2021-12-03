package com.example.controller;

import com.example.domain.Shipment;
import com.example.service.ShippingService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.List;
import java.util.stream.Collectors;

@Controller("/shipping")
public class ShippingController {
    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @Get("/shipments/recent/{count}")
    public HttpResponse<List<Shipment>> getRecentShipments(Long count) {
        return HttpResponse.ok(
            shippingService.getShipments().stream().limit(count).collect(Collectors.toList())
        );
    }
}