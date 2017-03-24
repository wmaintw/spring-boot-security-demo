package com.tw.security.demo.service;

import com.tw.security.demo.domain.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private List<Order> orders = new ArrayList<>();

    public OrderService() {
        orders.add(new Order(1, "order content of customer 1", 1, 1));
        orders.add(new Order(2, "another order content of customer 1", 1, 1));
        orders.add(new Order(3, "order content of customer 2", 2, 1));
        orders.add(new Order(100, "order content of customer 9 under d1 store2", 9, 1));
    }

    public List<Order> findAll() {
        return orders;
    }

    public Optional<Order> findOne(Integer id) {
        return orders.stream().filter(o -> o.getId().equals(id)).findFirst();
    }
}
