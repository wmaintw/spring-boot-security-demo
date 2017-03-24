package com.tw.security.demo.controller.advice;

import com.tw.security.demo.ErrorResponseDto;
import com.tw.security.demo.domain.exception.AuthenticationException;
import com.tw.security.demo.domain.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthenticationException(AuthenticationException ex) {
        log.warn(ex.toString());

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthorizationException(AccessDeniedException ex) {
        log.warn(ex.toString());

        ErrorResponseDto errorResponseDto = new ErrorResponseDto("403", ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, UNAUTHORIZED);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(BusinessException ex) {
        log.warn(ex.toString());

        ErrorResponseDto errorResponseDto = new ErrorResponseDto("100", ex.getMessage());
        return new ResponseEntity<ErrorResponseDto>(errorResponseDto, BAD_REQUEST);
    }

}
