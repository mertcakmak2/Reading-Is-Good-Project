package com.project.readingisgood.controller;

import com.project.readingisgood.entity.Customer;
import com.project.readingisgood.entity.Order;
import com.project.readingisgood.exception.exceptions.CustomerAlreadyExistException;
import com.project.readingisgood.exception.exceptions.CustomerNotFoundException;
import com.project.readingisgood.model.request.CustomerSaveRequestModel;
import com.project.readingisgood.model.request.PageableRequestModel;
import com.project.readingisgood.result.DataResult;
import com.project.readingisgood.result.SuccessDataResult;
import com.project.readingisgood.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(path = "")
    public ResponseEntity<DataResult> saveCustomer(@RequestBody @Valid CustomerSaveRequestModel customerSaveRequestModel) throws CustomerAlreadyExistException {
        var dataResult = new SuccessDataResult<Customer>("Customer saved.", customerService.saveCustomer(customerSaveRequestModel));
        return new ResponseEntity<DataResult>(dataResult, HttpStatus.CREATED);
    }

    @PostMapping(path = "/{customerId}/orders")
    public ResponseEntity<DataResult> retrieveOrdersOfCustomer(@PathVariable long customerId, @RequestBody PageableRequestModel pageableRequestModel) throws CustomerNotFoundException {
        var dataResult = new SuccessDataResult<Page<Order>>("Fetched customer orders.", customerService.retrieveOrdersOfCustomer(customerId, pageableRequestModel));
        return new ResponseEntity<DataResult>(dataResult, HttpStatus.OK);
    }


}
