package com.tw.security.demo.domain.dto;

import com.tw.security.demo.domain.UserRole;

public class TokenDto {
    private Integer userId;
    private String tokenId;
    private String username;
    private UserRole userRole;

    public TokenDto() {
    }

    public TokenDto(String tokenId, String username, Integer userId, UserRole role) {
        this.userId = userId;
        this.tokenId = tokenId;
        this.username = username;
        this.userRole = role;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "TokenDto{" +
                "userId=" + userId +
                ", tokenId='" + tokenId + '\'' +
                ", username='" + username + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
