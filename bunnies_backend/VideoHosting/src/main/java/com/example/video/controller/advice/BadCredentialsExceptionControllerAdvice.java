package com.example.video.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadCredentialsExceptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BadCredentialsExceptionDTO employeeNotFoundHandler(BadCredentialsException ex) {
        return new BadCredentialsExceptionDTO(ex.getMessage());
    }

    @AllArgsConstructor
    @Data
    private static class BadCredentialsExceptionDTO {

        String info;

    }

}
