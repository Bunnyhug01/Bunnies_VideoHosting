package com.example.video.service.impl;

import com.example.video.controller.advice.exception.GradeAlreadyExists;
import com.example.video.controller.advice.exception.GradeNotExists;
import com.example.video.entity.Grade;
import com.example.video.service.LikeService;
import com.example.video.service.UserService;
import com.example.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {

    private final UserService userService;
    private final VideoService videoService;

    @Transactional
    public void addLike(Long userId, Long videoId) {
        var user = userService.findById(userId);
        var video = videoService.findById(videoId);
        if (user.getLikes().contains(video))
            throw new GradeAlreadyExists(userId, videoId, Grade.LIKE);
        if (user.getDislikes().contains(video))
            throw new GradeAlreadyExists(userId, videoId, Grade.DISLIKE);
        user.getLikes().add(video);
        video.setLikes(video.getLikes() + 1);
        userService.save(user);
        videoService.save(video);
    }

    @Transactional
    public void removeLike(Long userId, Long videoId) {
        var user = userService.findById(userId);
        var video = videoService.findById(videoId);
        if (!user.getLikes().contains(video))
            throw new GradeNotExists(userId, videoId, Grade.LIKE);
        user.getLikes().remove(video);
        video.setLikes(video.getLikes() - 1);
        userService.save(user);
        videoService.save(video);
    }

    @Transactional(readOnly = true)
    public boolean hasLike(Long userId, Long videoId) {
        var user = userService.findById(userId);
        var video = videoService.findById(videoId);
        return user.getLikes().contains(video);
    }

}
