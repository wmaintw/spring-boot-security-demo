package com.tw.security.demo.dao;

import com.tw.security.demo.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void create(Order order) {
        jdbcTemplate.update("insert into orders(content, owner_id, store_id, status) values(?, ?, ?, ?)",
                order.getContent(), order.getOwnerId(), order.getStoreId(), order.getStatus());
    }

    public Optional<Order> findLatest(Integer ownerId, Integer storeId) {
        List<Order> orders = jdbcTemplate.query("select * from orders where owner_id = ? and store_id = ?", new Object[]{ownerId, storeId},
                (rs, rowNum) -> new Order(rs.getInt("id"), rs.getString("content"), rs.getInt("owner_id"), rs.getInt("store_id")));

        if (orders.size() > 0) {
            return Optional.of(orders.get(0));
        }

        return Optional.empty();
    }
}
