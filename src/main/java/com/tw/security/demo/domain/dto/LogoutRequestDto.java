package com.tw.security.demo.domain.dto;

public class LogoutRequestDto {
    private String tokenId;

    public LogoutRequestDto() {
    }

    public LogoutRequestDto(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
