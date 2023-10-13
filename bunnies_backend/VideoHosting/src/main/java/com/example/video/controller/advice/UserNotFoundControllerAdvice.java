package com.example.video.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserNotFoundControllerAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public UserNotFoundExceptionDTO employeeNotFoundHandler(UserNotFoundException ex) {
        return new UserNotFoundExceptionDTO(ex.getMessage());
    }

    @AllArgsConstructor
    @Data
    private static class UserNotFoundExceptionDTO {

        String info;

    }

}
