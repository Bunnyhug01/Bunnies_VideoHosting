package com.example.video.controller.advice.exception;

public class UserNotSubscribe extends RuntimeException {

    public UserNotSubscribe(Long userId, Long chanelId) {
        super("user = " + userId + " channel = " + chanelId);
    }

}
