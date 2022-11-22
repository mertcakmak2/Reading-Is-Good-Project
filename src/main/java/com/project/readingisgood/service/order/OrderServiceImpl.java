package com.project.readingisgood.service.order;

import com.project.readingisgood.entity.Book;
import com.project.readingisgood.entity.Customer;
import com.project.readingisgood.entity.Order;

import com.project.readingisgood.exception.exceptions.BookNotFoundException;
import com.project.readingisgood.exception.exceptions.CustomerNotFoundException;
import com.project.readingisgood.exception.exceptions.OrderNotFoundException;
import com.project.readingisgood.model.enums.OrderStatesEnum;
import com.project.readingisgood.model.request.OrderSaveRequestModel;
import com.project.readingisgood.model.request.PageableRequestModel;
import com.project.readingisgood.repository.OrderRepository;
import com.project.readingisgood.service.book.BookService;
import com.project.readingisgood.service.customer.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final BookService bookService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, @Lazy CustomerService customerService, BookService bookService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.bookService = bookService;
    }

    @Override
    public Order orderBook(OrderSaveRequestModel orderSaveRequestModel) throws CustomerNotFoundException, BookNotFoundException {
        Customer customer = customerService.findCustomerById(orderSaveRequestModel.getCustomerId());
        List<Book> books = bookService.findBooksByIdIn(orderSaveRequestModel.getBookIds());
        checkBooks(orderSaveRequestModel.getBookIds(), books);

        Order order = new Order(OrderStatesEnum.RECEIVED, customer);
        order.addBooks(books);
        orderRepository.save(order);
        logger.info("Order saved with Received state. ");
        return order;
    }

    @Override
    public Order findOrderById(long orderId) throws OrderNotFoundException {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    @Override
    public void updateOrderState(OrderStatesEnum state, long id){
        orderRepository.updateOrderState(state,id);
    }

    @Override
    public Page<Order> findOrdersByCustomerId(long customerId, PageableRequestModel pageableRequestModel) {
        Pageable paging = PageRequest.of(pageableRequestModel.getPage(), pageableRequestModel.getSize(), Sort.by("id"));
        return orderRepository.findOrdersByCustomer_Id(customerId, paging);
    }

    @Override
    public List<Order> findOrdersByDateInterval(Date beginDate, Date endDate ) {
        return orderRepository.findOrdersByDateInterval(beginDate,endDate);
    }

    public void checkBooks(List<Long> bookIds, List<Book> books) throws BookNotFoundException {
        List<Long> wrongIds = new ArrayList<>();
        for (long bookId : bookIds) {
            var id = books.stream().filter(i -> i.getId() == bookId).findFirst();
            if (id.isEmpty()) wrongIds.add(bookId);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Book(s) not found.Ids: ").append(wrongIds.toString());
        if (!wrongIds.isEmpty()) throw new BookNotFoundException(stringBuilder.toString());
    }
}
