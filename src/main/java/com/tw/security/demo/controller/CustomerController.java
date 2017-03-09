package com.tw.security.demo.controller;

import com.tw.security.demo.domain.Customer;
import com.tw.security.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Secured({"ROLE_ADMIN"})
    @GetMapping()
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Customer getCustomer(@PathVariable("id") Integer id) throws Exception {
        return customerService.find(id);
    }
}
