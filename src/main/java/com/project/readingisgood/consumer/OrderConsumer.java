package com.project.readingisgood.consumer;


import com.project.readingisgood.entity.Book;
import com.project.readingisgood.entity.Order;
import com.project.readingisgood.model.enums.OrderStatesEnum;
import com.project.readingisgood.service.order.OrderService;
import com.project.readingisgood.service.stock.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConsumer {

    Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    private final StockService stockService;
    private final OrderService orderService;

    public OrderConsumer(StockService stockService, OrderService orderService) {
        this.stockService = stockService;
        this.orderService = orderService;
    }

    @KafkaListener(topics = "order-topic", concurrency = "${spring.kafka.consumer.level.concurrency:3}", properties = {"spring.json.value.default.type=com.project.readingisgood.entity.Order"})
    public void orderTopicListener(Order order) throws InterruptedException {
        Thread.sleep(2000);

        List<Book> books = order.getBooks();
        var bookIds = books.stream().map(b -> b.getId()).collect(Collectors.toList());
        var noStockBooks = stockService.findNoStockByBookIds(bookIds);
        if (!noStockBooks.isEmpty()) {
            logger.info("No stock, order will cancel.");
            orderService.updateOrderState(OrderStatesEnum.CANCELED, order.getId());
            return;
        }

        for (Book book : books) {
            stockService.decreaseStockByBookId(book.getId());
        }
        logger.info("Order will set as Created.");
        orderService.updateOrderState(OrderStatesEnum.CREATED, order.getId());
    }

}