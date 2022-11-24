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
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Service
public class CustomerServiceImpl implements CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, OrderService orderService, ModelMapper modelMapper,@Lazy PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Customer saveCustomer(CustomerSaveRequestModel customerSaveRequestModel) throws CustomerAlreadyExistException {
        validateCustomer(customerSaveRequestModel);
        Customer customer = modelMapper.map(customerSaveRequestModel, Customer.class);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
        logger.info("Customer saved. {} ", customer.getEmail());
        return customer;
    }

    @Override
    public Customer findCustomerById(long customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

    @Override
    public Page<Order> retrieveOrdersOfCustomer(long customerId, PageableRequestModel pageableRequestModel) throws CustomerNotFoundException {
        Customer customer = findCustomerById(customerId);
        Page<Order> orders = orderService.findOrdersByCustomerId(customer.getId(), pageableRequestModel);
        logger.info("Retrieve orders of {} .", customer.getEmail());
        return orders;
    }

    private void validateCustomer(CustomerSaveRequestModel customerSaveRequestModel) throws CustomerAlreadyExistException {
        Customer customer = findCustomerByEmail(customerSaveRequestModel.getEmail());
        if(customer != null) throw new CustomerAlreadyExistException("Customer already exist");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = findCustomerByEmail(email);
        return new User(customer.getEmail(), customer.getPassword(), new ArrayList<>());
    }

    @PostConstruct
    private void savaInitCustomer() {
        var email = "testuser@gmail.com";
        Customer existCustomer = customerRepository.findCustomerByEmail(email);
        if(existCustomer != null) return;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Customer customer = new Customer();
        customer.setName("testuser");
        customer.setEmail(email);
        customer.setPassword(encoder.encode("testpassword"));
    }
}
