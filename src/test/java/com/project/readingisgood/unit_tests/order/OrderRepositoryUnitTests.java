package com.project.readingisgood.unit_tests.order;

import com.project.readingisgood.entity.Customer;
import com.project.readingisgood.entity.Order;
import com.project.readingisgood.model.enums.OrderStatesEnum;
import com.project.readingisgood.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderRepositoryUnitTests {

    @MockBean
    OrderRepository orderRepository;

    @Test
    void findOrdersByCustomer_Id_test_should_find_orders_by_customer_id(){
        Pageable paging = PageRequest.of(0, 5);
        var customerId = 1;
        Mockito.when(orderRepository.findOrdersByCustomer_Id(customerId, paging))
                .thenReturn(getPageWithOrder());

        Page<Order> pageWithOrder = orderRepository.findOrdersByCustomer_Id(customerId,paging);
        var order = pageWithOrder.getContent().get(0);

        assertEquals(customerId, order.getCustomer().getId());
    }

    @Test
    void findOrdersByDateInterval_test_should_find_orders_by_date_interval(){
        var beginDate = new Date();
        var endDate = new Date();
        Mockito.when(orderRepository.findOrdersByDateInterval(beginDate, endDate))
                .thenReturn(getOrders());

        var orders = orderRepository.findOrdersByDateInterval(beginDate, endDate);
        assertNotNull(orders);
    }

    Page<Order> getPageWithOrder(){
        var pageWithOrder = new PageImpl<Order>(getOrders());
        return pageWithOrder;
    }

    List<Order> getOrders(){
        var order = new Order(1, OrderStatesEnum.RECEIVED,new Date(),getCustomer(),null);
        List<Order> orders = Arrays.asList(order);
        return orders;
    }

    Customer getCustomer(){
        return new Customer(1,"mert","mertcakmak2@gmail.com");
    }
}
