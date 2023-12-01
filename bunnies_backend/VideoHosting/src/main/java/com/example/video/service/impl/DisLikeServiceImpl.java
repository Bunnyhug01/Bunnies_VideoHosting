package com.example.video.service.impl;

import com.example.video.controller.advice.exception.GradeAlreadyExists;
import com.example.video.controller.advice.exception.GradeNotExists;
import com.example.video.controller.advice.exception.UserNotFoundException;
import com.example.video.controller.advice.exception.VideoNotFoundException;
import com.example.video.entity.Grade;
import com.example.video.repository.UserRepository;
import com.example.video.repository.VideoRepository;
import com.example.video.service.DisLikeService;
import com.example.video.service.UserService;
import com.example.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class DisLikeServiceImpl implements DisLikeService {

    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    @Transactional
    public void addDisLike(Long userId, Long videoId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        var video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException(videoId));
        if (user.getDislikes().contains(video))
            throw new GradeAlreadyExists(userId, videoId, Grade.DISLIKE);
        if (user.getLikes().contains(video))
            throw new GradeAlreadyExists(userId, videoId, Grade.LIKE);
        user.getDislikes().add(video);
        video.setDislikes(video.getDislikes() + 1);
        userRepository.save(user);
        videoRepository.save(video);
    }

    @Transactional
    public void removeDisLike(Long userId, Long videoId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        var video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException(videoId));
        if (!user.getDislikes().contains(video))
            throw new GradeNotExists(userId, videoId, Grade.DISLIKE);
        user.getDislikes().remove(video);
        video.setDislikes(video.getDislikes() - 1);
        userRepository.save(user);
        videoRepository.save(video);
    }

    @Transactional(readOnly = true)
    public boolean hasDisLike(Long userId, Long videoId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        var video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException(videoId));
        return user.getDislikes().contains(video);
    }

}
