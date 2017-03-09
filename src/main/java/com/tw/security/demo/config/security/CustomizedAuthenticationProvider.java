package com.tw.security.demo.config.security;

import com.tw.security.demo.domain.User;
import com.tw.security.demo.domain.UserRole;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomizedAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthenticatedAuthenticationToken authenticationToken = (PreAuthenticatedAuthenticationToken) authentication;
        User principal = (User) authenticationToken.getPrincipal();
        System.out.println("user " + principal.getUsername() + " authenticated via token.");

        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserRole role = principal.getRole();
        ArrayList<String> roles = new ArrayList<>();
        roles.add(role.toString());
        System.out.println("user has this role: " + role.toString());
        System.out.println("user has these roles: " + roles);

        CustomizedAuthentication customizedAuthentication = new CustomizedAuthentication(principal, roles);
        securityContext.setAuthentication(customizedAuthentication);
        return customizedAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println("determine if current auth provider could handle this request");
        boolean isSupport = PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
        System.out.println("determine result is:" + isSupport);
        return isSupport;
    }
}
