package com.example.video.service.impl;

import com.example.video.controller.advice.exception.ForbiddenException;
import com.example.video.controller.advice.exception.VideoNotFoundException;
import com.example.video.entity.Video;
import com.example.video.repository.VideoRepository;
import com.example.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

    @Override
    public long count() {
        return videoRepository.count();
    }

    @Override
    public long countCanSee(Long userId) {
        return videoRepository.findAll().stream().filter(x -> !x.isPrivate() || x.getOwner().getId() == userId).count();
    }

    @Override
    public Video findRandom() {
        final var random = new Random();
        var all = videoRepository.findAll();
        return all.get(random.nextInt(all.size()));
    }

    @Override
    public Video findRandomCanSee(Long userId) {
        final var random = ThreadLocalRandom.current();
        var all = videoRepository.findAll().stream().filter(x -> !x.isPrivate() || Objects.equals(x.getOwner().getId(), userId)).toList();
        return all.get(random.nextInt(all.size()));
    }

    @Override
    public Video getById(long videoId) {
        return videoRepository.getReferenceById(videoId);
    }

}
