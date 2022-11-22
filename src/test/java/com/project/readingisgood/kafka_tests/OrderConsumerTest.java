package com.project.readingisgood.kafka_tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.readingisgood.consumer.OrderConsumer;
import com.project.readingisgood.entity.Order;
import com.project.readingisgood.model.enums.OrderStatesEnum;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@EmbeddedKafka
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderConsumerTest {

    private Producer<String, String> producer;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private OrderConsumer orderConsumer;

    @Captor
    ArgumentCaptor<Order> orderArgumentCaptor;

    @BeforeAll
    void setUp() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        producer = new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new StringSerializer()).createProducer();
    }

    @Test
    void todo_consume_from_kafka_test() throws JsonProcessingException, InterruptedException {

        Order order = new Order(1L, OrderStatesEnum.RECEIVED,new Date(),null, null);
        String message = objectMapper.writeValueAsString(order);
        producer.send(new ProducerRecord<>("order-topic", 0, String.valueOf(order.getId()), message));
        producer.flush();

        verify(orderConsumer, timeout(5000).times(1))
                .orderTopicListener(orderArgumentCaptor.capture());

        Order orderValue = orderArgumentCaptor.getValue();
        assertNotNull(orderValue);
    }

    @AfterAll
    void shutdown() {
        producer.close();
    }
}
