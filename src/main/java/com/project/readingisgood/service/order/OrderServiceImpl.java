package com.project.readingisgood.service.order;

import com.project.readingisgood.entity.Order;

import com.project.readingisgood.exception.exceptions.OrderNotFoundException;
import com.project.readingisgood.model.enums.OrderStatesEnum;
import com.project.readingisgood.model.request.PageableRequestModel;
import com.project.readingisgood.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
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
}
