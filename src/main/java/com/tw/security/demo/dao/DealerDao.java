package com.tw.security.demo.dao;

import com.tw.security.demo.domain.DealerUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DealerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Optional<DealerUser> findDealerUser(Integer serviceConsultantId) {
        List<DealerUser> dealerUsers = jdbcTemplate.query("select * from dealer_users where user_id = ?", new Object[]{serviceConsultantId}, (rs, rowNum) ->
                new DealerUser(rs.getInt("id"), rs.getInt("user_id"), rs.getString("name"), rs.getInt("store_id"))
        );

        if (dealerUsers.size() > 0) {
            return Optional.of(dealerUsers.get(0));
        }

        return Optional.empty();
    }
}
