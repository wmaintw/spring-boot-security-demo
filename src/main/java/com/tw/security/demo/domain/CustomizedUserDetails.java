package com.tw.security.demo.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomizedUserDetails extends org.springframework.security.core.userdetails.User {

    public CustomizedUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomizedUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
