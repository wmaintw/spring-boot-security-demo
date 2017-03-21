package com.tw.security.demo.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.savedrequest.NullRequestCache;

@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private CustomizedAuthenticationProvider customizedAuthenticationProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customizedAuthenticationProvider);
    }

    @Configuration
    public static class CustomizedWebSecurityConfigureAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private CustomizedAuthenticationFilter customizedAuthenticationFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.requestCache().requestCache(new NullRequestCache());
            http.addFilter(customizedAuthenticationFilter);

            http.csrf().disable().httpBasic().disable();

            http.authorizeRequests()
                    .antMatchers("/token", "/info").permitAll()
                    .anyRequest().authenticated();
        }
    }
}
