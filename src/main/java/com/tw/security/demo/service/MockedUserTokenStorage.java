package com.tw.security.demo.service;

import com.tw.security.demo.domain.Dealer;
import com.tw.security.demo.domain.DealerUser;
import com.tw.security.demo.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.tw.security.demo.domain.UserRole.*;

@Service
public class MockedUserTokenStorage {
    private HashMap<String, User> loggedInUsers = new HashMap<>();

    public MockedUserTokenStorage() {
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

    public HashMap<String, User> getLoggedInUsers() {
        return loggedInUsers;
    }

    public void delete(String token) {
        loggedInUsers.remove(token);
    }
}
