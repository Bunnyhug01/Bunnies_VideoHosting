package com.example.video.service.impl;

import com.example.video.entity.Video;
import com.example.video.entity.VideoHistory;
import com.example.video.service.UserService;
import com.example.video.service.VideoService;
import com.example.video.service.ViewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@AllArgsConstructor
@Service
public class ViewServiceImpl implements ViewService {

    private final VideoService videoService;
    private final UserService userService;

    @Transactional(readOnly = true)
    @Override
    public Collection<Video> getLine(Long userId, Long videoId) {
        return getLine(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Video> getLine(Long userId) {
        var result = new HashSet<Video>();
        while (result.size() < Math.min(10, videoService.countCanSee(userId))) {
            result.add(videoService.findRandomCanSee(userId));
        }
        return result;
    }

    @Transactional
    @Override
    public void addAnonymousView(Long videoId) {
        var video = videoService.findById(videoId);
        video.setViews(video.getViews() + 1);
        videoService.save(video);
    }

    @Transactional
    @Override
    public void addView(Long userId, Long videoId) {
        var video = videoService.findById(videoId);
        var user = userService.findById(userId);
        var history = VideoHistory.builder().video(video).date(new Date()).build();
        user.getHistory().add(history);
        video.setViews(video.getViews() + 1);
        videoService.save(video);
        userService.save(user);
    }

}
