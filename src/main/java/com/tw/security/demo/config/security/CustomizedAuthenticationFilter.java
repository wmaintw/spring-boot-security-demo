package com.tw.security.demo.config.security;

import com.tw.security.demo.domain.User;
import com.tw.security.demo.service.MockedUserTokenStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomizedAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    @Autowired
    MockedUserTokenStorage userTokenStorage;

    @Autowired
    public CustomizedAuthenticationFilter(@Qualifier(value = "authenticationManager") AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        System.out.println("try to extract token from http request header");
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("token is: " + token);

        if (token == null) {
            System.out.println("abort customized authentication filter as token is missing");
            return null;
        }

        System.out.println("start to find corresponding user by this token");
        User user = userTokenStorage.findByToken(token);
        System.out.println("user is: " + user);

        if (user == null) {
            System.out.println("current request will be rejected as no corresponding logged in user found.");
        } else {
            System.out.println("current request will be approved");
        }

        return user;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        //no password should be returned
        return null;
    }
}
