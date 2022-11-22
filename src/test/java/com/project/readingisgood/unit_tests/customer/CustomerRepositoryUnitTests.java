package com.project.readingisgood.unit_tests.customer;

import com.project.readingisgood.entity.Customer;
import com.project.readingisgood.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerRepositoryUnitTests {

    @MockBean
    CustomerRepository customerRepository;

    @Test
    void findCustomerByEmail_test_should_find_customer_by_email(){
        Customer customer = getCustomer();

        Mockito.when(customerRepository.findCustomerByEmail(customer.getEmail())).thenReturn(customer);
        Customer savedCustomer = this.customerRepository.findCustomerByEmail(customer.getEmail());

        assertEquals(customer.getEmail(), savedCustomer.getEmail());
    }

    Customer getCustomer(){
        return new Customer(1,"mert","mertcakmak2@gmail.com");
    }

}
