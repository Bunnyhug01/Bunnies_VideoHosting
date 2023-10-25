package com.example.video.controller.advice.exception;

public class VideoNotFoundException extends RuntimeException {

    public VideoNotFoundException(Long id) {
        super("Could not find video with id = " + id);
    }

}
