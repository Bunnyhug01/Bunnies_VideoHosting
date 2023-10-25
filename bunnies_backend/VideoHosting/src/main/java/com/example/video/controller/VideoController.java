package com.example.video.controller;

import com.example.video.controller.advice.exception.ForbiddenException;
import com.example.video.entity.User;
import com.example.video.entity.Video;
import com.example.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin("${cross.origin.url}")
@AllArgsConstructor
@RestController
public class VideoController {

    private final VideoService service;

    @GetMapping("/videos")
    public Collection<Video> getAll(Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        if (user.hasRole("ADMIN"))
            return service.findAll();
        return service.findAll().stream().filter(x -> !x.isPrivate() || x.getOwner().equals(user)).toList();
    }

    @GetMapping("/videos/{id}")
    public Video getOne(@PathVariable Long id, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        var video = service.findById(id);
        if (!user.hasRole("ADMIN") && video.isPrivate() && !video.getOwner().equals(user))
            throw new ForbiddenException("video is private");
        return video;
    }

    @DeleteMapping("/videos/{id}")
    public void deleteOne(@PathVariable Long id, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        if (user.hasRole("ADMIN"))
            service.delete(id);
        service.delete(user.getId(), id);
    }

    @PostMapping("/videos")
    public Video create(@RequestBody Video video, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        video.setOwner(user);
        return service.save(video);
    }

}
