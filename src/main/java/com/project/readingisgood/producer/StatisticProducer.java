package com.project.readingisgood.producer;

import com.project.readingisgood.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class StatisticProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${statistic.topic.name}")
    private String statisticTopic;

    @Autowired
    public StatisticProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToStatisticTopic(Order order) {
        kafkaTemplate.send(statisticTopic, String.valueOf(order.getId()), order);
    }

}