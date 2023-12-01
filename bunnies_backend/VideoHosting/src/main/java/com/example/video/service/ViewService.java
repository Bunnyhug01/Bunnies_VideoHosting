package com.example.video.service;

import com.example.video.entity.Video;

import java.util.Collection;

public interface ViewService {

    Collection<Video> getLine(long userId, long videoId);

    Collection<Video> getLine(long userId);

    void addAnonymousView(long videoId);

    void addView(long userId, long videoId);

}
