package com.tw.security.demo.config.security;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(securedEnabled = true)
public class RoleBasedSecurityConfig {
}
