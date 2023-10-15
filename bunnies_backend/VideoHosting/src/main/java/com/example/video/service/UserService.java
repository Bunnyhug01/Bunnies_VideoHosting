package com.example.video.service;

import com.example.video.entity.User;

import java.util.Collection;

public interface UserService {

    User findById(Long id);

    void save(User user);

    Collection<User> findAll();

}
