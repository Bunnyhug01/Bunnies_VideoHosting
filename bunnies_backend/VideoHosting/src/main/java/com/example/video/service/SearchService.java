package com.example.video.service;

import com.example.video.entity.Video;

import java.util.List;

public interface SearchService {

    List<Video> searchVideoByName(String name);

}
