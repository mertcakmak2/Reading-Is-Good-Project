package com.project.readingisgood.service.customer;

import com.project.readingisgood.entity.Customer;
import com.project.readingisgood.entity.Order;
import com.project.readingisgood.exception.exceptions.CustomerAlreadyExistException;
import com.project.readingisgood.exception.exceptions.CustomerNotFoundException;
import com.project.readingisgood.model.request.CustomerSaveRequestModel;
import com.project.readingisgood.model.request.PageableRequestModel;
import com.project.readingisgood.repository.CustomerRepository;
import com.project.readingisgood.service.order.OrderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, OrderService orderService, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
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

    @Override
    public Page<Order> retrieveOrdersOfCustomer(long customerId, PageableRequestModel pageableRequestModel) throws CustomerNotFoundException {
        Customer customer = findCustomerById(customerId);
        Page<Order> orders = orderService.findOrdersByCustomerId(customer.getId(), pageableRequestModel);
        return orders;
    }

    private void validateCustomer(CustomerSaveRequestModel customerSaveRequestModel) throws CustomerAlreadyExistException {
        Customer customer = findCustomerByEmail(customerSaveRequestModel.getEmail());
        if(customer != null) throw new CustomerAlreadyExistException("Customer already exist");
    }

}