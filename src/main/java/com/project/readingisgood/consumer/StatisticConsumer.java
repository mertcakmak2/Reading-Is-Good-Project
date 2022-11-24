package com.project.readingisgood.consumer;

import com.project.readingisgood.entity.Order;
import com.project.readingisgood.model.OrderStatistic;
import com.project.readingisgood.service.statistic.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class StatisticConsumer {

    Logger logger = LoggerFactory.getLogger(StatisticConsumer.class);

    private final StatisticService statisticService;

    @Autowired
    public StatisticConsumer(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @KafkaListener(topics = "${statistic.topic.name}", properties = {"spring.json.value.default.type=com.project.readingisgood.entity.Order"})
    public void statisticTopicListener(Order order) {
        var month = getOrderMonth(order.getCreated_at());

        var books = order.getBooks();
        var prices = books.stream().map(b -> b.getPrice() ).collect(Collectors.toList());
        var sumPrice = prices.stream().reduce(0d, Double::sum);
        OrderStatistic orderStatistic = new OrderStatistic(month, books.size(), sumPrice);
        logger.info("Save monthly statistic. Month: {}, Order Id: {}", month, order.getId());
        statisticService.saveMonthlyStatistic(orderStatistic);
    }

    private String getOrderMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
    }
}
