package com.tw.security.demo.service;

import com.tw.security.demo.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.tw.security.demo.domain.UserRole.*;

@Service
public class MockedUserTokenStorage {
    private List<User> allUsers = new ArrayList<User>();
    private HashMap<String, User> loggedInUsers = new HashMap<>();

    public MockedUserTokenStorage() {
        allUsers.add(new User(1, "weima", "123", ROLE_CUSTOMER));
        allUsers.add(new User(2, "wma", "123", ROLE_CUSTOMER));
        allUsers.add(new User(3, "vian", "123", ROLE_DEALER));
        allUsers.add(new User(4, "bob", "123", ROLE_ADMIN));
    }

    public Optional<User> findByUsername(String username) {
        return allUsers.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    public User findByToken(String token) {
        return loggedInUsers.get(token);
    }

    public void store(String token, User user) {
        if (loggedInUsers.containsKey(token)) {
            return;
        }

        loggedInUsers.put(token, user);
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public HashMap<String, User> getLoggedInUsers() {
        return loggedInUsers;
    }

    public void delete(String token) {
        loggedInUsers.remove(token);
    }
}
