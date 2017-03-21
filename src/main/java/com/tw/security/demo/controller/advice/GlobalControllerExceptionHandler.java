package com.tw.security.demo.controller.advice;

import com.tw.security.demo.ErrorResponseDto;
import com.tw.security.demo.domain.exception.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthenticationException(AuthenticationException ex) {
        log.warn(ex.toString());

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<ErrorResponseDto>(errorResponseDto, UNAUTHORIZED);
    }

}
