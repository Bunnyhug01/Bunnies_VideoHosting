package com.example.video.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VideoNotFoundControllerAdvice {

    @ResponseBody
    @ExceptionHandler(VideoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public VideoNotFoundExceptionDTO employeeNotFoundHandler(VideoNotFoundException ex) {
        return new VideoNotFoundExceptionDTO(ex.getMessage());
    }

    @AllArgsConstructor
    @Data
    private static class VideoNotFoundExceptionDTO {

        String info;

    }

}
