package com.example.video.controller;

import com.example.video.entity.User;
import com.example.video.entity.Video;
import com.example.video.service.UserService;
import com.example.video.service.ViewService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin("${cross.origin.url}")
@AllArgsConstructor
@RestController
public class ViewController {

    private ViewService service;
    private UserService userService;

    @GetMapping("/line")
    public Collection<Video> getLine(Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        return service.getLine(user.getId());
    }

    @PostMapping("/view/{id}")
    public void addView(@PathVariable Long id, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        service.addView(user.getId(), id);
    }

    @PostMapping("anonymous/view/{id}")
    public void addView(@PathVariable Long id) {
        service.addAnonymousView(id);
    }

    @GetMapping("/line/{id}")
    public Collection<Video> getLine(@PathVariable Long id, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        return service.getLine(user.getId(), id);
    }

}
