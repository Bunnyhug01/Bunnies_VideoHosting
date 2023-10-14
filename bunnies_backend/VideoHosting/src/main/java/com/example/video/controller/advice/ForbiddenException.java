package com.example.video.controller.advice;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String massage) {
        super(massage);
    }

    public ForbiddenException() {
        super("the current user is not owner");
    }

}
