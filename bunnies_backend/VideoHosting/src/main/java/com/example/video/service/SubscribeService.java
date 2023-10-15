package com.example.video.service;

public interface SubscribeService {

    void addSubscribe(Long userId, Long chanelId);

    void removeSubscribe(Long userId, Long chanelId);

    boolean hasSubscribe(Long userId, Long chanelId);
}
