package com.project.readingisgood.service.auth;

import com.project.readingisgood.entity.Customer;
import com.project.readingisgood.jwt.JwtUtil;
import com.project.readingisgood.model.request.LoginRequestModel;
import com.project.readingisgood.service.customer.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final CustomerService customerService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthServiceImpl(CustomerService customerService, JwtUtil jwtUtil, PasswordEncoder encoder) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    @Override
    public String login(LoginRequestModel loginRequestModel) {
        Customer customer = customerService.findCustomerByEmail(loginRequestModel.getEmail());
        checkCredentials(customer,loginRequestModel);
        logger.info("{} sign in.", customer.getEmail());
        return jwtUtil.generateToken(customer.getEmail());
    }

    public void checkCredentials(Customer customer, LoginRequestModel loginRequestModel){
        if(customer == null) throw new BadCredentialsException("Invalid email or password");
        boolean isPasswordMatch = encoder.matches(loginRequestModel.getPassword(), customer.getPassword());
        if(!isPasswordMatch) throw new BadCredentialsException("Invalid email or password");
    }
}
