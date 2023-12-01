package com.example.video.service.impl;

import com.example.video.controller.advice.exception.UserAlreadySubscribe;
import com.example.video.controller.advice.exception.UserNotFoundException;
import com.example.video.controller.advice.exception.UserNotSubscribe;
import com.example.video.repository.UserRepository;
import com.example.video.service.SubscribeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SubscribeServiceImpl implements SubscribeService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public void addSubscribe(Long userId, Long chanelId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        var chanel = userRepository.findById(chanelId)
                .orElseThrow(() -> new UserNotFoundException(chanelId));
        if (chanel.getSubscribers().contains(user))
            throw new UserAlreadySubscribe(userId, chanelId);
        chanel.getSubscribers().add(user);
        user.getSubscriptions().add(chanel);
        userRepository.save(chanel);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void removeSubscribe(Long userId, Long chanelId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        var chanel = userRepository.findById(chanelId)
                .orElseThrow(() -> new UserNotFoundException(chanelId));
        if (!chanel.getSubscribers().contains(user))
            throw new UserNotSubscribe(userId, chanelId);
        chanel.getSubscribers().remove(user);
        user.getSubscriptions().remove(chanel);
        userRepository.save(chanel);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean hasSubscribe(Long userId, Long chanelId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        var chanel = userRepository.findById(chanelId)
                .orElseThrow(() -> new UserNotFoundException(chanelId));
        return chanel.getSubscribers().contains(user);
    }

}
