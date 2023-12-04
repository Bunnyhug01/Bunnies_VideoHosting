package com.example.video.service.impl;

import com.example.video.controller.advice.exception.UserNotFoundException;
import com.example.video.controller.advice.exception.VideoNotFoundException;
import com.example.video.entity.Video;
import com.example.video.entity.VideoHistory;
import com.example.video.repository.UserRepository;
import com.example.video.repository.VideoRepository;
import com.example.video.service.VideoService;
import com.example.video.service.ViewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@Service
public class ViewServiceImpl implements ViewService {

    private final VideoService videoService;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    @Transactional(readOnly = true)
    @Override
    public Collection<Video> getLine(long userId, long videoId) {
        var result = new HashSet<Video>();
        var videos = videoService.getAllVideos();
        while (result.size() < Math.min(10, videos.size() - 1)) {
            var video = videos.get(ThreadLocalRandom.current().nextInt(videos.size()));
            if (Objects.equals(video.getId(), videoId))
                continue;
            result.add(video);
        }
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Video> getLine(long userId) {
        var result = new HashSet<Video>();
        var videos = videoService.getAllVideos();
        while (result.size() < Math.min(10, videos.size())) {
            var video = videos.get(ThreadLocalRandom.current().nextInt(videos.size()));
            result.add(video);
        }
        return result;
    }

    @Transactional
    @Override
    public void addAnonymousView(long videoId) {
        var video = videoService.getOneVideo(videoId);
        video.setViews(video.getViews() + 1);
        videoRepository.save(video);
    }

    @Transactional
    @Override
    public void addView(long userId, long videoId) {
        var video = videoRepository.findById(videoId)
                .orElseThrow(() -> new VideoNotFoundException(videoId));
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        var history = VideoHistory.builder().video(video).date(new Date()).build();
        user.getHistory().add(history);
        video.setViews(video.getViews() + 1);
        videoRepository.save(video);
        userRepository.save(user);
    }

}
