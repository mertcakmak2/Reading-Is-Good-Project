package com.project.readingisgood.producer;


import com.project.readingisgood.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public OrderProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToOrderTopic(Order order) {
        kafkaTemplate.send("order-topic", String.valueOf(order.getId()), order);
    }

}