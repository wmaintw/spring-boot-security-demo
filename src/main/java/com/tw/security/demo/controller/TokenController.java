package com.tw.security.demo.controller;

import com.tw.security.demo.domain.dto.LoginRequestDto;
import com.tw.security.demo.domain.dto.TokenDto;
import com.tw.security.demo.service.TokenService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    TokenService tokenService;

    @PostMapping()
    @ResponseStatus(CREATED)
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        TokenDto tokenDto = tokenService.grant(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        return new ResponseEntity<>(tokenDto, CREATED);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void logout(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            tokenService.revoke(token);
        }

        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
