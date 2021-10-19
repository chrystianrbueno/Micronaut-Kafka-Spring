package com.example.messaging;
import com.example.domain.Shipment;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
@KafkaClient
public interface ShipmentProducer {
        @Topic("shipping-topic")
        void sendMessage(Shipment shipment);
}