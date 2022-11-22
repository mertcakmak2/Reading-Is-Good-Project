package com.project.readingisgood.unit_tests.customer;

import com.project.readingisgood.entity.Customer;
import com.project.readingisgood.exception.exceptions.CustomerAlreadyExistException;
import com.project.readingisgood.exception.exceptions.CustomerNotFoundException;
import com.project.readingisgood.model.request.CustomerSaveRequestModel;
import com.project.readingisgood.service.customer.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    Customer getCustomer(){
        return new Customer(1,"mert","mertcakmak2@gmail.com","123", null);
    }
}
