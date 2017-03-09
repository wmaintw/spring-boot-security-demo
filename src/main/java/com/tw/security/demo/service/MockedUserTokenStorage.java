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
        allUsers.add(new User(100, "weima", "123", ROLE_CUSTOMER));
        allUsers.add(new User(200, "vian", "123", ROLE_DEALER));
        allUsers.add(new User(300, "bob", "123", ROLE_ADMIN));
    }

    public Optional<User> findByUsername(String username) {
        return allUsers.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    public User findByToken(String token) {
        return loggedInUsers.get(token);
    }

    public void store(String token, User user) {
        if (loggedInUsers.containsKey(token)) {
            System.out.println("user session exist, not need to store again.");
            return;
        }

        loggedInUsers.put(token, user);

        System.out.println("loggedInUsers after store(): " + loggedInUsers);
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public HashMap<String, User> getLoggedInUsers() {
        return loggedInUsers;
    }

    public void delete(String token) {
        loggedInUsers.remove(token);
        System.out.println("loggedInUsers after delete(): " + loggedInUsers);
    }
}
