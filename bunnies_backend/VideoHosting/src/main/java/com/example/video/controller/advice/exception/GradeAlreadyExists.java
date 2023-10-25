package com.example.video.controller.advice.exception;

import com.example.video.entity.Grade;

public class GradeAlreadyExists extends RuntimeException {

    public GradeAlreadyExists(Long userId, Long videoId, Grade grade) {
        super("user = " + userId + " video = " + videoId + " grade = " + grade);
    }

}
