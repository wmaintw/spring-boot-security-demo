package com.tw.security.demo.dao;

import com.tw.security.demo.domain.User;
import com.tw.security.demo.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<User> findByUsername(String username) {
        List<User> users = jdbcTemplate.query("select * from users where username = ?", new Object[]{username}, (rs, rowNum) ->
                new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), UserRole.valueOf(rs.getString("role")))
        );

        if (users.size() > 0) {
            return Optional.of(users.get(0));
        }

        return Optional.empty();
    }

    public void insert(User user) {
        jdbcTemplate.update("insert into users(id, username, password, role) values(?, ?, ?, ?)",
                user.getId(), user.getUsername(), user.getPassword(), user.getRole().toString());
    }
}
