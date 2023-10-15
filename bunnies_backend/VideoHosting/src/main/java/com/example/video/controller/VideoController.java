package com.example.video.controller;

import com.example.video.entity.User;
import com.example.video.entity.Video;
import com.example.video.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin("${cross.origin.url}")
@AllArgsConstructor
@RestController
public class VideoController {

    private final VideoService service;

    @GetMapping("/videos")
    public Collection<Video> getAll() {
        return service.findAll();
    }

    @GetMapping("/videos/{id}")
    public Video getOne(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/videos/{id}")
    public void deleteOne(@PathVariable Long id, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        service.delete(user.getId(), id);
    }

    @DeleteMapping("admin/videos/{id}")
    public void deleteOneAdmin(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/videos")
    public Video create(@RequestBody Video video, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        video.setOwner(user);
        return service.save(video);
    }

}
