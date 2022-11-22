package com.project.readingisgood.service.customer;

import com.project.readingisgood.entity.Customer;
import com.project.readingisgood.exception.exceptions.CustomerAlreadyExistException;
import com.project.readingisgood.exception.exceptions.CustomerNotFoundException;
import com.project.readingisgood.model.request.CustomerSaveRequestModel;

public interface CustomerService {

    Customer saveCustomer(CustomerSaveRequestModel customerSaveRequestModel) throws CustomerAlreadyExistException;
    Customer findCustomerById(long customerId) throws CustomerNotFoundException;
    Customer findCustomerByEmail(String email);
}
