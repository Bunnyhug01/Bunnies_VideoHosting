package com.example.video.controller.advice;

public class UserNotSubscribe extends RuntimeException {

    public UserNotSubscribe(Long userId, Long chanelId) {
        super("user = " + userId + " channel = " + chanelId);
    }

}
