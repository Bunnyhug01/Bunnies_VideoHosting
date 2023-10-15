package com.example.video.controller;

import com.example.video.entity.User;
import com.example.video.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@CrossOrigin("${cross.origin.url}")
@AllArgsConstructor
@RestController
public class UserController {

    private final UserService service;

    @GetMapping("/users")
    public Collection<User> getAll() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User getOne(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/users/me")
    public User getMe(Authentication authentication) {
        return service.findById(((User) authentication.getPrincipal()).getId());
    }

}
