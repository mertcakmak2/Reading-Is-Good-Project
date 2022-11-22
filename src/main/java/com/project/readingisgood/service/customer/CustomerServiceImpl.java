package com.project.readingisgood.service.customer;

import com.project.readingisgood.entity.Customer;
import com.project.readingisgood.exception.exceptions.CustomerAlreadyExistException;
import com.project.readingisgood.exception.exceptions.CustomerNotFoundException;
import com.project.readingisgood.model.request.CustomerSaveRequestModel;
import com.project.readingisgood.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Customer saveCustomer(CustomerSaveRequestModel customerSaveRequestModel) throws CustomerAlreadyExistException {
        validateCustomer(customerSaveRequestModel);
        Customer customer = modelMapper.map(customerSaveRequestModel, Customer.class);
        customerRepository.save(customer);
        logger.info("Customer saved. {} ", customer.getEmail());
        return customer;
    }

    @Override
    public Customer findCustomerById(long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return customer;
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

    private void validateCustomer(CustomerSaveRequestModel customerSaveRequestModel) throws CustomerAlreadyExistException {
        Customer customer = findCustomerByEmail(customerSaveRequestModel.getEmail());
        if(customer != null) throw new CustomerAlreadyExistException("Customer already exist");
    }

}
