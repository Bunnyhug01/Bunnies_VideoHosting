package com.example.video.security;

public class InvalidJwtAuthenticationException extends RuntimeException {

    public InvalidJwtAuthenticationException(String massage) {
        super(massage);
    }

}
