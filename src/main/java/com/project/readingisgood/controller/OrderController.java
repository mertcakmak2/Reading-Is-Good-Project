package com.project.readingisgood.controller;

import com.project.readingisgood.entity.Order;
import com.project.readingisgood.exception.exceptions.BookNotFoundException;
import com.project.readingisgood.exception.exceptions.CustomerNotFoundException;
import com.project.readingisgood.exception.exceptions.OrderNotFoundException;
import com.project.readingisgood.model.request.OrderSaveRequestModel;
import com.project.readingisgood.result.DataResult;
import com.project.readingisgood.result.SuccessDataResult;
import com.project.readingisgood.service.order.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "")
    public ResponseEntity<DataResult> orderBook(@RequestBody @Valid OrderSaveRequestModel orderSaveRequestModel) throws BookNotFoundException, CustomerNotFoundException {
        var dataResult = new SuccessDataResult<Order>("Order received.", orderService.orderBook(orderSaveRequestModel));
        return new ResponseEntity<DataResult>(dataResult, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{orderId}")
    public ResponseEntity<DataResult> findOrderById(@PathVariable long orderId) throws OrderNotFoundException {
        var dataResult = new SuccessDataResult<Order>("Order found by id.", orderService.findOrderById(orderId));
        return new ResponseEntity<DataResult>(dataResult, HttpStatus.OK);
    }

    @GetMapping(path = "")
    public ResponseEntity<DataResult> findOrdersByDateInterval(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date beginDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate ) {
        var dataResult = new SuccessDataResult<List<Order>>("Orders found by date interval.", orderService.findOrdersByDateInterval(beginDate,endDate));
        return new ResponseEntity<DataResult>(dataResult, HttpStatus.OK);
    }
}
