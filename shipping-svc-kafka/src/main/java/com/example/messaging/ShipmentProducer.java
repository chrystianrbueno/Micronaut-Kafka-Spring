package com.example.messaging;
import com.example.domain.Shipment;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Property;
import io.micronaut.messaging.annotation.MessageBody;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

@KafkaClient
public interface ShipmentProducer {
        @Topic("shipping-topic")
        void sendMessage(Shipment shipment);
}