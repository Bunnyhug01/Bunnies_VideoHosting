package com.example.video.service.impl;

import com.example.video.controller.advice.exception.UserAlreadySubscribe;
import com.example.video.controller.advice.exception.UserNotSubscribe;
import com.example.video.service.SubscribeService;
import com.example.video.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SubscribeServiceImpl implements SubscribeService {

    private final UserService userService;

    @Transactional
    @Override
    public void addSubscribe(Long userId, Long chanelId) {
        var user = userService.findById(userId);
        var chanel = userService.findById(chanelId);
        if (chanel.getSubscribers().contains(user))
            throw new UserAlreadySubscribe(userId, chanelId);
        chanel.getSubscribers().add(user);
        user.getSubscriptions().add(chanel);
        userService.save(chanel);
        userService.save(user);
    }

    @Transactional
    @Override
    public void removeSubscribe(Long userId, Long chanelId) {
        var user = userService.findById(userId);
        var chanel = userService.findById(chanelId);
        if (!chanel.getSubscribers().contains(user))
            throw new UserNotSubscribe(userId, chanelId);
        chanel.getSubscribers().remove(user);
        user.getSubscriptions().remove(chanel);
        userService.save(chanel);
        userService.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean hasSubscribe(Long userId, Long chanelId) {
        var user = userService.findById(userId);
        var chanel = userService.findById(chanelId);
        return chanel.getSubscribers().contains(user);
    }

}
