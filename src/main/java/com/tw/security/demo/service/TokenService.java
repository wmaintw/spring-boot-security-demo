package com.tw.security.demo.service;

import com.tw.security.demo.domain.dto.TokenDto;
import com.tw.security.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private MockedUserTokenStorage userTokenStorage;

    public TokenDto grant(String username, String password) throws Exception {
        Optional<User> user = userTokenStorage.findByUsername(username);
        if (!user.isPresent()) {
            throw new Exception("Invalid username or password.");
        }

        User foundUser = user.get();
        if (!foundUser.getPassword().equals(password)) {
            throw new Exception("Invalid username or password.");
        }

        System.out.println("username and password is correct, build token for current user.");

        String tokenId = buildToken(username);
        userTokenStorage.store(tokenId, foundUser);

        return new TokenDto(tokenId, username);
    }

    public void revoke(String token) {
        userTokenStorage.delete(token);
    }

    private String buildToken(String username) {
        return "token-id-for-" + username;
    }
}
