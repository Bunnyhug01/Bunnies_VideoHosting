package com.example.video.controller.advice.exception;

import com.example.video.entity.Grade;

public class GradeNotExists extends RuntimeException {

    public GradeNotExists(Long userId, Long videoId, Grade grade) {
        super("user = " + userId + " video = " + videoId + " grade = " + grade);
    }

}
