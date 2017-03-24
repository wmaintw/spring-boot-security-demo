package com.tw.security.demo.domain;

public enum UserRole {
    ROLE_CUSTOMER("ROLE_CUSTOMER"),
    ROLE_SERVICE_CONSULTANT("ROLE_SERVICE_CONSULTANT"),
    ROLE_DEALER_GENERAL_MANAGER("ROLE_DEALER_GENERAL_MANAGER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
