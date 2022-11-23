package com.project.readingisgood.unit_tests.customer;

import com.project.readingisgood.controller.CustomerController;
import com.project.readingisgood.entity.Customer;
import com.project.readingisgood.entity.Order;
import com.project.readingisgood.exception.exceptions.CustomerAlreadyExistException;
import com.project.readingisgood.exception.exceptions.CustomerNotFoundException;
import com.project.readingisgood.model.enums.OrderStatesEnum;
import com.project.readingisgood.model.request.CustomerSaveRequestModel;
import com.project.readingisgood.model.request.PageableRequestModel;
import com.project.readingisgood.result.DataResult;
import com.project.readingisgood.result.SuccessDataResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerUnitTests {

    @MockBean
    CustomerController customerController;

    @Test
    void saveCustomer_test_method_should_save_customer() throws CustomerAlreadyExistException, CustomerNotFoundException {
        Customer customer = new Customer(1, "mert", "mertcakmak2@gmail.com", "123", null);
        var customerSaveRequest = new CustomerSaveRequestModel(
                customer.getName(),
                customer.getEmail(),
                customer.getPassword());

        var dataResult = new SuccessDataResult<Customer>("Customer saved.", customer);
        var expectedResponse = new ResponseEntity<DataResult>(dataResult, HttpStatus.CREATED);

        Mockito.when(customerController.saveCustomer(customerSaveRequest))
                .thenReturn(expectedResponse);
        var response = customerController.saveCustomer(customerSaveRequest);

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void retrieveOrdersOfCustomer_test_method_retrieve_customer_orders() throws CustomerNotFoundException {
        Customer customer = new Customer(1, "mert", "mertcakmak2@gmail.com", "123", null);
        var pageableRequest = new PageableRequestModel(0, 5);

        var dataResult = new SuccessDataResult<Page<Order>>("Fetched customer orders.", getPageWithOrder());
        var expectedResponse = new ResponseEntity<DataResult>(dataResult, HttpStatus.OK);

        Mockito.when(customerController.retrieveOrdersOfCustomer(customer.getId(),pageableRequest))
                .thenReturn(expectedResponse);
        var response = customerController.retrieveOrdersOfCustomer(customer.getId(),pageableRequest);

        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    Order getOrder() {
        return new Order(1, OrderStatesEnum.RECEIVED, new Date(), null, null);
    }

    Page<Order> getPageWithOrder() {
        var orders = Arrays.asList(getOrder());
        var pageWithOrder = new PageImpl<Order>(orders);
        return pageWithOrder;
    }
}
