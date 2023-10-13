package com.example.video.controller;

public class VideoNotFoundException extends RuntimeException {

    public VideoNotFoundException(Long id) {
        super("Could not find video with id = " + id);
    }

}
