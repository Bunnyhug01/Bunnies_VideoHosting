package com.example.video.service;

import com.example.video.entity.Video;

import java.util.Collection;

public interface ViewService {

    Collection<Video> getLine(Long userId, Long videoId);

    Collection<Video> getLine(Long userId);

    void addAnonymousView(Long videoId);

    void addView(Long userId, Long videoId);

}
