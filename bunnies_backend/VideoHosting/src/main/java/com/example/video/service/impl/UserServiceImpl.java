package com.example.video.service.impl;

import com.example.video.controller.advice.exception.UserNotFoundException;
import com.example.video.dto.request.UserReplaceRequest;
import com.example.video.entity.User;
import com.example.video.repository.UserRepository;
import com.example.video.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@AllArgsConstructor
@Component("userService")
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public User getOneUser(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public Collection<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public void deleteUser(long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public User replaceUser(long id, UserReplaceRequest request) {
        var user = getOneUser(id);
        var username = request.getUsername();
        if (username != null)
            user.setUsername(username);
        var password = request.getPassword();
        if (password != null)
            user.setUsername(password);
        return repository.save(user);
    }

}