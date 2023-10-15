package com.example.video.service.impl;

import com.example.video.controller.advice.ForbiddenException;
import com.example.video.controller.advice.VideoNotFoundException;
import com.example.video.entity.Video;
import com.example.video.repository.VideoRepository;
import com.example.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@AllArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    @Override
    public Video findById(Long id) {
        return videoRepository.findById(id).orElseThrow(() -> new VideoNotFoundException(id));
    }

    @Override
    public Video save(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public Collection<Video> findAll() {
        return videoRepository.findAll();
    }

    @Transactional
    @Override
    public void delete(Long userId, Long videoId) {
        var video = findById(videoId);
        if (userId != video.getOwner().getId())
            throw new ForbiddenException();
        videoRepository.delete(video);
    }

    @Override
    public void delete(Long videoId) {
        var video = findById(videoId);
        videoRepository.delete(video);
    }

}
