package com.example.video.controller.advice.exception;

public class UserAlreadySubscribe extends RuntimeException {

    public UserAlreadySubscribe(Long userId, Long chanelId) {
        super("user = " + userId + " channel = " + chanelId);
    }

}
