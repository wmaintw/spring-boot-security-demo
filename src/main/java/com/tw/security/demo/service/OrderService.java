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
        orders.add(new Order(1, "order content 1", 101));
        orders.add(new Order(2, "order content 2", 101));
        orders.add(new Order(3, "order content 3", 101));
        orders.add(new Order(4, "order content 4", 102));
    }

    public List<Order> findAll() {
        return orders;
    }

    public Optional<Order> findOne(Integer id) {
        return orders.stream().filter(o -> o.getId().equals(id)).findFirst();
    }
}
