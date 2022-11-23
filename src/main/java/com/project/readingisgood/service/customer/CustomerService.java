package com.project.readingisgood.service.customer;

import com.project.readingisgood.entity.Customer;
import com.project.readingisgood.entity.Order;
import com.project.readingisgood.exception.exceptions.CustomerAlreadyExistException;
import com.project.readingisgood.exception.exceptions.CustomerNotFoundException;
import com.project.readingisgood.model.request.CustomerSaveRequestModel;
import com.project.readingisgood.model.request.PageableRequestModel;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomerService extends UserDetailsService {

    Customer saveCustomer(CustomerSaveRequestModel customerSaveRequestModel) throws CustomerAlreadyExistException;
    Customer findCustomerById(long customerId) throws CustomerNotFoundException;
    Customer findCustomerByEmail(String email);
    Page<Order> retrieveOrdersOfCustomer(long customerId, PageableRequestModel pageableRequestModel) throws CustomerNotFoundException;

}
