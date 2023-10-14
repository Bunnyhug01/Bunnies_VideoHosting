package com.example.video.controller;

import com.example.video.controller.advice.UserNotFoundException;
import com.example.video.entity.User;
import com.example.video.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@CrossOrigin("${cross.origin.url}")
@AllArgsConstructor
@RestController
public class UserController {

    private final UserRepository repository;

    @GetMapping("/users")
    public Collection<User> getAll() {
        return repository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getOne(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

}
