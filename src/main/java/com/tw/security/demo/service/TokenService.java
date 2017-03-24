package com.tw.security.demo.service;

import com.tw.security.demo.dao.UserDao;
import com.tw.security.demo.domain.User;
import com.tw.security.demo.domain.dto.TokenDto;
import com.tw.security.demo.domain.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private MockedUserTokenStorage userTokenStorage;

    @Autowired
    private UserDao userDao;

    public TokenDto grant(String username, String password) throws Exception {
        Optional<User> user = userDao.findByUsername(username);
        if (!user.isPresent()) {
            throw new AuthenticationException("Invalid username or password.");
        }

        User foundUser = user.get();
        if (!foundUser.getPassword().equals(password)) {
            throw new AuthenticationException("Invalid username or password.");
        }

        String tokenId = buildToken(username);
        userTokenStorage.store(tokenId, foundUser);

        return new TokenDto(tokenId, username, foundUser.getId(), foundUser.getRole());
    }

    public void revoke(String token) {
        userTokenStorage.delete(token);
    }

    private String buildToken(String username) {
        return "token-id-for-" + username + "-" + UUID.randomUUID();
    }

}
