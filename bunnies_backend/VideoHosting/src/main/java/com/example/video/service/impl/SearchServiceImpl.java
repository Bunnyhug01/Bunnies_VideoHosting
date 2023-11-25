package com.example.video.service.impl;

import com.example.video.entity.User;
import com.example.video.entity.Video;
import com.example.video.service.SearchService;
import com.example.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {

    private final VideoService service;

    @Override
    public List<Video> searchVideoByName(String name) {
        return service.findAll().stream().filter(x -> x.getTitle().contains(name)).toList();
    }

    @Override
    public List<Video> searchLikedVideoByName(String name, User user) {
        return service.findAll().stream().filter(x -> user.getLikes().contains(x)).filter(x -> x.getTitle().contains(name)).toList();
    }

}
