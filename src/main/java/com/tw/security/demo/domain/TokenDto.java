package com.tw.security.demo.domain;

public class TokenDto {
    private String tokenId;
    private String firstName;

    public TokenDto() {
    }

    public TokenDto(String tokenId, String firstName) {
        this.tokenId = tokenId;
        this.firstName = firstName;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
