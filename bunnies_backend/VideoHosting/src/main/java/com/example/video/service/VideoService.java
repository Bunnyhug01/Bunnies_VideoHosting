package com.example.video.service;

import com.example.video.entity.Video;

import java.util.Collection;

public interface VideoService {

    Video findById(Long id);

    Video save(Video video);

    Collection<Video> findAll();

    void delete(Long userId, Long videoId);

}
