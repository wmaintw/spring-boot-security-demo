package com.tw.security.demo.domain.exception;

public class BusinessException extends Exception {
    private String message;

    public BusinessException(String message) {
        this.message = message;
    }
}
