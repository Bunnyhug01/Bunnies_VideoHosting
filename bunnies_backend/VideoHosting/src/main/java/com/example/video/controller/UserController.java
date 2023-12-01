package com.example.video.controller;

import com.example.video.controller.annotations.UserById;
import com.example.video.dto.request.UserReplaceRequest;
import com.example.video.entity.User;
import com.example.video.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin("${cross.origin.url}")
@AllArgsConstructor
@RestController
public class UserController {

    private final UserService service;

    @GetMapping("/users")
    public Collection<User> getAll() {
        return service.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getOne(@PathVariable long id) {
        return service.getOneUser(id);
    }

    @GetMapping("/users/me")
    public User getMe(Authentication authentication) {
        return service.getOneUser(((User) authentication.getPrincipal()).getId());
    }

    @UserById
    @PutMapping("/users/{id}")
    public User replace(@PathVariable long id, @RequestBody UserReplaceRequest request) {
        return service.replaceUser(id, request);
    }

    @UserById
    @DeleteMapping("/users/{id}")
    public void deleteOne(@PathVariable long id) {
        service.deleteUser(id);
    }

}
