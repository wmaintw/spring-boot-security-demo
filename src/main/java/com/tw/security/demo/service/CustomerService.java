package com.tw.security.demo.service;

import com.tw.security.demo.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final ArrayList<Customer> customers = new ArrayList<>();

    public CustomerService() {
        customers.add(new Customer(1, "wei", "ma"));
        customers.add(new Customer(2, "john", "smith"));
        customers.add(new Customer(3, "bob", "zhang"));
    }

    public List<Customer> findAll() {
        return customers;
    }

    public Customer find(Integer id) throws Exception {
        Optional<Customer> customer = customers.stream().filter(c -> c.getId().equals(id)).findFirst();
        if (customer.isPresent()) {
            return customer.get();
        }

        throw new Exception("Customer not found.");
    }
}
