package com.example.video.service;

public interface LikeService {

    void addLike(Long userId, Long videoId);

    void removeLike(Long userId, Long videoId);

    boolean hasLike(Long userId, Long videoId);

}
