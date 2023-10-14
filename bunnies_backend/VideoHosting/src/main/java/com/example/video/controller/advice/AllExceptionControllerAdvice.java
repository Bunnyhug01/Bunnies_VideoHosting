package com.example.video.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AllExceptionControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDTO processRuntimeException(RuntimeException e) {
        log.error("Unsupported Exception:", e);
        return newException(e);
    }

    private ExceptionDTO newException(RuntimeException e) {
        return new ExceptionDTO(e.getClass().getName(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({UserNotFoundException.class, VideoNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDTO notFoundException(RuntimeException e) {
        return newException(e);
    }

    @ResponseBody
    @ExceptionHandler({ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionDTO forbiddenException(ForbiddenException e) {
        return newException(e);
    }

    @AllArgsConstructor
    @Data
    private static class ExceptionDTO {

        String type;
        String info;

    }

}
