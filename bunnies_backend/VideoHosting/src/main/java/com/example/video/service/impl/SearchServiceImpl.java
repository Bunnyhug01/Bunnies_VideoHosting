package com.example.video.service.impl;

import com.example.video.entity.User;
import com.example.video.entity.Video;
import com.example.video.repository.VideoRepository;
import com.example.video.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {

    private final VideoRepository videoRepository;

    @Override
    public List<Video> searchVideoByName(String name) {
        final var n = name.toLowerCase();
        return videoRepository.findAll().stream().filter(x -> x.getTitle().toLowerCase().contains(n)).toList();
    }

    @Override
    public List<Video> searchLikedVideoByName(String name, User user) {
        final var n = name.toLowerCase();
        return videoRepository.findAll().stream().filter(x -> user.getLikes().contains(x)).filter(x -> x.getTitle().toLowerCase().contains(n)).toList();
    }

    @Override
    public List<Video> searchVideoByNameInHistory(String name, User user) {
        final var n = name.toLowerCase();
        return videoRepository.findAll().stream().filter(x -> user.getHistory().stream().anyMatch(h -> Objects.equals(h.getVideo(), x)))
                .filter(x -> x.getTitle().toLowerCase().contains(n)).toList();
    }

    @Override
    public List<Video> searchVideoByOwner(String name, User user) {
        final var n = name.toLowerCase();
        return user.getVideos().stream().filter(x -> x.getTitle().toLowerCase().contains(n)).toList();
    }

}
