package com.project.readingisgood.service.order;

import com.project.readingisgood.entity.Order;
import com.project.readingisgood.exception.exceptions.BookNotFoundException;
import com.project.readingisgood.exception.exceptions.CustomerNotFoundException;
import com.project.readingisgood.exception.exceptions.OrderNotFoundException;
import com.project.readingisgood.model.enums.OrderStatesEnum;
import com.project.readingisgood.model.request.OrderSaveRequestModel;
import com.project.readingisgood.model.request.PageableRequestModel;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface OrderService {

    Order orderBook(OrderSaveRequestModel orderSaveRequestModel) throws CustomerNotFoundException, BookNotFoundException;
    Order findOrderById(long orderId) throws OrderNotFoundException;
    void updateOrderState(OrderStatesEnum state, long id);
    Page<Order> findOrdersByCustomerId(long customerId, PageableRequestModel pageableRequestModel);
    List<Order> findOrdersByDateInterval(Date beginDate, Date endDate);
}
