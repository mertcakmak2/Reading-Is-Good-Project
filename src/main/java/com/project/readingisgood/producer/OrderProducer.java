package com.project.readingisgood.producer;


import com.project.readingisgood.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${order.topic.name}")
    private String orderTopic;

    @Autowired
    public OrderProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToOrderTopic(Order order) {
        kafkaTemplate.send(orderTopic, String.valueOf(order.getId()), order);
    }

}