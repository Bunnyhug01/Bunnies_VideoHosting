package com.example.video.service;

import com.example.video.entity.User;
import com.example.video.entity.Video;

import java.util.List;

public interface SearchService {

    List<Video> searchVideoByName(String name);

    List<Video> searchLikedVideoByName(String name, User user);
    List<Video> searchVideoByNameInHistory(String name, User user);

}
