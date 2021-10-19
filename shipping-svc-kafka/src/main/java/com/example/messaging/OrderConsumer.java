    package com.example.messaging;

    import com.example.domain.Order;
    import com.example.domain.Shipment;
    import com.example.service.ShippingService;
    import io.micronaut.configuration.kafka.annotation.KafkaKey;
    import io.micronaut.configuration.kafka.annotation.KafkaListener;
    import io.micronaut.configuration.kafka.annotation.OffsetReset;
    import io.micronaut.configuration.kafka.annotation.Topic;
    import io.micronaut.context.annotation.Property;
    import io.micronaut.messaging.annotation.MessageBody;
    import io.reactivex.rxjava3.core.Single;
    import lombok.extern.slf4j.Slf4j;
    import org.apache.kafka.clients.consumer.ConsumerConfig;
    import org.apache.kafka.clients.producer.ProducerConfig;

    @Slf4j
    @KafkaListener(offsetReset = OffsetReset.EARLIEST)
    public class OrderConsumer {
        private final ShippingService shippingService;

        public OrderConsumer(ShippingService shippingService){
            this.shippingService = shippingService;
        }

        @Topic("order-topic")
        public Single<Order> receive(@MessageBody Single<Order> orderFlowable){
            return orderFlowable.doOnSuccess(order ->{
                log.info("Order {} received", order.getId());
                log.info("Creating shipment for order {}....", order.getId());
//                /* shipping is slow! */
//                Thread.sleep(15*1000);
                Shipment shipment = shippingService.newShipment(order);
                log.info("Shipped order {} with shipment ID {}...", order.getId(), shipment.getId());
            });
        }
}