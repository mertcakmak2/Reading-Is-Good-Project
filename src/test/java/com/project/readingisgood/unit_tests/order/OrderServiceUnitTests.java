package com.project.readingisgood.unit_tests.order;

import com.project.readingisgood.entity.Order;
import com.project.readingisgood.exception.exceptions.OrderNotFoundException;
import com.project.readingisgood.model.enums.OrderStatesEnum;
import com.project.readingisgood.model.request.PageableRequestModel;
import com.project.readingisgood.service.order.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderServiceUnitTests {

    @MockBean
    OrderService orderService;

    @Test
    void findOrderById_test_should_find_order_by_id() throws OrderNotFoundException {
        var order = getOrder();
        var id = 1L;
        Mockito.when(orderService.findOrderById(id)).thenReturn(order);
        var existOrder = orderService.findOrderById(id);

        assertEquals(id, existOrder.getId());
    }

    @Test
    void findOrderById_test_should_throw_order_not_found_ex() {
        var order = getOrder();
        var id = 1L;
        try {
            Mockito.when(orderService.findOrderById(id)).thenReturn(order);
            orderService.findOrderById(id);
        } catch (OrderNotFoundException e) {
            assertEquals("Order not found", e.getMessage());
        }
    }

    @Test
    void findOrdersByCustomerId_test_should_find_orders_by_customer_id() {
        var customerId = 1L;
        var pageWithOrders = getPageWithOrder();
        var pageableRequest = new PageableRequestModel(0,5);
        Mockito.when(orderService.findOrdersByCustomerId(customerId,pageableRequest))
                .thenReturn(pageWithOrders);

        var orders = orderService.findOrdersByCustomerId(customerId,pageableRequest);
        assertNotNull(orders);
    }

    @Test
    void findOrdersByDateInterval_test_should_find_orders_by_date_interval() {
        var beginDate = new Date();
        var endDate = new Date();
        var orders = getOrders();
        Mockito.when(orderService.findOrdersByDateInterval(beginDate, endDate))
                .thenReturn(orders);

        assertNotNull(orders);
    }

    Order getOrder() {
        return new Order(1, OrderStatesEnum.RECEIVED, new Date(), null, null);
    }

    Page<Order> getPageWithOrder() {
        var orders = Arrays.asList(getOrder());
        var pageWithOrder = new PageImpl<Order>(orders);
        return pageWithOrder;
    }

    List<Order> getOrders(){
        List<Order> orders = Arrays.asList(getOrder());
        return orders;
    }

}