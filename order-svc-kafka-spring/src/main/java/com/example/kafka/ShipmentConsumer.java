package com.example.kafka;

import com.example.domain.Shipment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShipmentConsumer {

    @KafkaListener(topics = "shipping-topic", groupId = "group_id")
    public void consume(@Payload Shipment shipment){
        log.info("Shippment return -> {}", shipment);
    }
}
