package com.example.video.service;

public interface DisLikeService {

    void addDisLike(Long userId, Long videoId);

    void removeDisLike(Long userId, Long videoId);

    boolean hasDisLike(Long userId, Long videoId);

}
