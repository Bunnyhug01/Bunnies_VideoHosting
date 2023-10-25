package com.example.video.service.impl;

import com.example.video.controller.advice.exception.GradeAlreadyExists;
import com.example.video.controller.advice.exception.GradeNotExists;
import com.example.video.entity.Grade;
import com.example.video.service.DisLikeService;
import com.example.video.service.UserService;
import com.example.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class DisLikeServiceImpl implements DisLikeService {

    private final UserService userService;
    private final VideoService videoService;

    @Transactional
    public void addDisLike(Long userId, Long videoId) {
        var user = userService.findById(userId);
        var video = videoService.findById(videoId);
        if (user.getDislikes().contains(video))
            throw new GradeAlreadyExists(userId, videoId, Grade.DISLIKE);
        if (user.getLikes().contains(video))
            throw new GradeAlreadyExists(userId, videoId, Grade.LIKE);
        user.getDislikes().add(video);
        video.setDislikes(video.getDislikes() + 1);
        userService.save(user);
        videoService.save(video);
    }

    @Transactional
    public void removeDisLike(Long userId, Long videoId) {
        var user = userService.findById(userId);
        var video = videoService.findById(videoId);
        if (!user.getDislikes().contains(video))
            throw new GradeNotExists(userId, videoId, Grade.DISLIKE);
        user.getDislikes().remove(video);
        video.setDislikes(video.getDislikes() - 1);
        userService.save(user);
        videoService.save(video);
    }

    @Transactional(readOnly = true)
    public boolean hasDisLike(Long userId, Long videoId) {
        var user = userService.findById(userId);
        var video = videoService.findById(videoId);
        return user.getDislikes().contains(video);
    }

}
