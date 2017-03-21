package com.tw.security.demo.domain.dto;

public class TokenDto {
    private String tokenId;
    private String username;

    public TokenDto() {
    }

    public TokenDto(String tokenId, String username) {
        this.tokenId = tokenId;
        this.username = username;
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
}
