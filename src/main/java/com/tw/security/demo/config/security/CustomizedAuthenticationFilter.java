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
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token == null) {
            return null;
        }

        return userTokenStorage.findByToken(token);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        //no password should be returned
        return null;
    }
}
