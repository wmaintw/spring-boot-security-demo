package com.tw.security.demo.service;

import com.tw.security.demo.domain.User;
import com.tw.security.demo.domain.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private MockedUserTokenStorage userTokenStorage;

    public User findBy(String token) throws Exception {
        User user = userTokenStorage.findByToken(token);
        if (user == null) {
            throw new AuthenticationException("Invalid session.");
        }

        return user;
    }
}
