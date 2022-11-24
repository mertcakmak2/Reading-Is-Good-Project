package com.project.readingisgood.unit_tests.order;

import com.project.readingisgood.controller.OrderController;
import com.project.readingisgood.entity.Order;
import com.project.readingisgood.exception.exceptions.BookNotFoundException;
import com.project.readingisgood.exception.exceptions.CustomerNotFoundException;
import com.project.readingisgood.exception.exceptions.OrderNotFoundException;
import com.project.readingisgood.model.enums.OrderStatesEnum;
import com.project.readingisgood.model.request.OrderSaveRequestModel;
import com.project.readingisgood.result.DataResult;
import com.project.readingisgood.result.SuccessDataResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerUnitTests {

    @MockBean
    OrderController orderController;

    @Test
    void orderBook_test_method_should_order_book() throws BookNotFoundException, CustomerNotFoundException {
        var customerId = 1L;
        List<Long> ids = Arrays.asList(1L);
        Order order = new Order(1, OrderStatesEnum.RECEIVED, new Date(), null, null);
        var orderSaveReq = new OrderSaveRequestModel(customerId,ids);

        var dataResult = new SuccessDataResult<Order>("Order received.", order);
        var expectedResponse = new ResponseEntity<DataResult<Order>>(dataResult, HttpStatus.CREATED);

        Mockito.when(orderController.orderBook(orderSaveReq)).thenReturn(expectedResponse);
        var response = orderController.orderBook(orderSaveReq);

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void findOrderById_test_method_should_find_order_by_id() throws  OrderNotFoundException {
        var orderId = 1L;
        Order order = new Order(orderId, OrderStatesEnum.RECEIVED, new Date(), null, null);

        var dataResult = new SuccessDataResult<Order>("Order found by id.", order);
        var expectedResponse = new ResponseEntity<DataResult<Order>>(dataResult, HttpStatus.OK);

        Mockito.when(orderController.findOrderById(orderId)).thenReturn(expectedResponse);
        var response = orderController.findOrderById(orderId);

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void findOrdersByDateInterval_test_method_should_find_orders_by_date_interval() throws  OrderNotFoundException {
        var orderId = 1L;
        Order order = new Order(orderId, OrderStatesEnum.RECEIVED, new Date(), null, null);
        var orders = Arrays.asList(order);
        var dataResult = new SuccessDataResult<List<Order>>("Orders found by date interval.", orders);
        var expectedResponse = new ResponseEntity<DataResult<List<Order>>>(dataResult, HttpStatus.OK);

        var beginDate = new Date();
        var endDate = new Date();
        Mockito.when(orderController.findOrdersByDateInterval(beginDate,endDate)).thenReturn(expectedResponse);
        var response = orderController.findOrdersByDateInterval(beginDate,endDate);

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }
}
