package com.project.readingisgood.unit_tests.customer;

import com.project.readingisgood.entity.Customer;
import com.project.readingisgood.entity.Order;
import com.project.readingisgood.exception.exceptions.CustomerAlreadyExistException;
import com.project.readingisgood.exception.exceptions.CustomerNotFoundException;
import com.project.readingisgood.model.enums.OrderStatesEnum;
import com.project.readingisgood.model.request.CustomerSaveRequestModel;
import com.project.readingisgood.model.request.PageableRequestModel;
import com.project.readingisgood.service.customer.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerServiceUnitTests {

    @MockBean
    CustomerService customerService;

    @Test
    void saveCustomer_test_should_save_customer() throws CustomerAlreadyExistException {
        var customer = getCustomer();
        var customerSaveRequest = new CustomerSaveRequestModel(
                customer.getName(),
                customer.getEmail(),
                customer.getPassword());
        Mockito.when(customerService.saveCustomer(customerSaveRequest)).thenReturn(customer);
        var savedCustomer = customerService.saveCustomer(customerSaveRequest);

        assertEquals(customer.getEmail(),customerSaveRequest.getEmail());
    }

    @Test
    void saveCustomer_test_should_throw_customer_already_exist_ex() {
        var customer = getCustomer();
        var customerSaveRequest = new CustomerSaveRequestModel(
                customer.getName(),
                customer.getEmail(),
                customer.getPassword());
        try {
            Mockito.when(customerService.saveCustomer(customerSaveRequest))
                    .thenThrow(new CustomerAlreadyExistException("Customer already exist"));
            customerService.saveCustomer(customerSaveRequest);
        } catch (CustomerAlreadyExistException e) {
            assertEquals("Customer already exist", e.getMessage());
        }
    }

    @Test
    void findCustomerById_test_should_find_customer_by_id() throws CustomerNotFoundException {
        var customer = getCustomer();
        var id = 1L;
        Mockito.when(customerService.findCustomerById(id)).thenReturn(customer);
        var existCustomer = customerService.findCustomerById(id);

        assertEquals(id,existCustomer.getId());
    }

    @Test
    void findCustomerById_test_should_throw_customer_not_found_ex() {
        var customer = getCustomer();
        var id = 1L;
        try {
            Mockito.when(customerService.findCustomerById(id)).thenReturn(customer);
            customerService.findCustomerById(id);
        } catch (CustomerNotFoundException e) {
            assertEquals("Customer not found", e.getMessage());
        }
    }

    @Test
    void findCustomerByEmail_test_should_find_customer_by_email() throws CustomerNotFoundException {
        var customer = getCustomer();
        var email = "mertcakmak2@gmail.com";
        Mockito.when(customerService.findCustomerByEmail(email)).thenReturn(customer);
        var existCustomer = customerService.findCustomerByEmail(email);

        assertEquals(email,existCustomer.getEmail());
    }

    @Test
    void retrieveOrdersOfCustomer_test_should_retrieve_orders_of_customer() throws CustomerNotFoundException {
        var customerId = 1L;
        var pageWithOrders = getPageWithOrder();
        var pageableRequest = new PageableRequestModel(0,5);
        Mockito.when(customerService.retrieveOrdersOfCustomer(customerId,pageableRequest))
                .thenReturn(pageWithOrders);

        var orderPage = customerService.
                retrieveOrdersOfCustomer(customerId,pageableRequest);

        var order = orderPage.getContent().get(0);
        assertEquals(customerId, order.getCustomer().getId());
    }

    @Test
    void retrieveOrdersOfCustomer_test_should_throw_customer_not_found_ex() {
        var customerId = 1L;
        var pageWithOrders = getPageWithOrder();
        var pageableRequest = new PageableRequestModel(0,5);
        try {
            Mockito.when(customerService.retrieveOrdersOfCustomer(customerId,pageableRequest))
                    .thenThrow(new CustomerNotFoundException("Customer not found."));
            customerService.retrieveOrdersOfCustomer(customerId,pageableRequest);
        } catch (CustomerNotFoundException e) {
            assertEquals("Customer not found.", e.getMessage());
        }
    }

    Customer getCustomer(){
        return new Customer(1,"mert","mertcakmak2@gmail.com","123", null);
    }

    Order getOrder() {
        return new Order(1, OrderStatesEnum.RECEIVED, new Date(), getCustomer(), null);
    }

    Page<Order> getPageWithOrder() {
        var orders = Arrays.asList(getOrder());
        var pageWithOrder = new PageImpl<Order>(orders);
        return pageWithOrder;
    }
}
