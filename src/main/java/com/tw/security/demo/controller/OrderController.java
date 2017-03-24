package com.tw.security.demo.controller;

import com.tw.security.demo.aop.annotation.CheckOwnership;
import com.tw.security.demo.aop.annotation.ParamToCheck;
import com.tw.security.demo.domain.Order;
import com.tw.security.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Secured("ROLE_ADMIN")
    @GetMapping()
    public List<Order> getOrders() {
        return orderService.findAll();
    }

    @Secured({"ROLE_CUSTOMER", "ROLE_SERVICE_CONSULTANT", "ROLE_ADMIN"})
    @CheckOwnership(value = "OrderOwnershipChecker")
    @GetMapping("/{id}")
    public Order getOrder(@ParamToCheck @PathVariable("id") Integer id) throws Exception {
        Optional<Order> order = orderService.findOne(id);
        if (!order.isPresent()) {
            throw new Exception("Order not found");
        }

        return order.get();
    }
}
