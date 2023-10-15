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
    public Object processRuntimeException(RuntimeException e) {
        log.error("Unsupported Exception:", e);
        return newException(e);
    }

    private Object newException(RuntimeException e) {
        final var m = e.getMessage();
        if (m == null)
            return new ExceptionDTO(e.getClass().getSimpleName());
        return new InfoExceptionDTO(e.getClass().getSimpleName(), m);
    }

    @ResponseBody
    @ExceptionHandler({UserNotFoundException.class, VideoNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object notFoundException(RuntimeException e) {
        return newException(e);
    }

    @ResponseBody
    @ExceptionHandler({UserAlreadyExists.class, GradeAlreadyExists.class, GradeNotExists.class, UserAlreadySubscribe.class, UserNotSubscribe.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object clientException(RuntimeException e) {
        return newException(e);
    }

    @ResponseBody
    @ExceptionHandler({ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Object forbiddenException(RuntimeException e) {
        return newException(e);
    }

    @ResponseBody
    @ExceptionHandler({NotValidJWT.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Object unauthorizedException(RuntimeException e) {
        return newException(e);
    }

    @AllArgsConstructor
    @Data
    private static class ExceptionDTO {

        String type;

    }

    @AllArgsConstructor
    @Data
    private static class InfoExceptionDTO {

        String type;
        String info;

    }

}
