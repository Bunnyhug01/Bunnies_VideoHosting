package com.example.video.controller.advice;

public class UserAlreadyExists extends RuntimeException {

    public UserAlreadyExists(Long userId, String username) {
        super("id = " + userId + " username = " + username);
    }

}
