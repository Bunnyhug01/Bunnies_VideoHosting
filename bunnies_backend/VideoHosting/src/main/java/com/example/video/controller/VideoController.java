package com.example.video.controller;

import com.example.video.controller.advice.ForbiddenException;
import com.example.video.controller.advice.VideoNotFoundException;
import com.example.video.entity.User;
import com.example.video.entity.Video;
import com.example.video.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin("${cross.origin.url}")
@AllArgsConstructor
@RestController
public class VideoController {

    private final VideoRepository repository;

    @GetMapping("/videos")
    public Collection<Video> getAll() {
        return repository.findAll();
    }

    @GetMapping("/videos/{id}")
    public Video getOne(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new VideoNotFoundException(id));
    }

    @DeleteMapping("/videos/{id}")
    public void deleteOne(@PathVariable Long id, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        var video = repository.findById(id).orElseThrow(() -> new VideoNotFoundException(id));
        if (!user.equals(video.getOwner()))
            throw new ForbiddenException();
        repository.delete(video);
    }

    @PostMapping("/videos")
    public Video create(@RequestBody Video video, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        video.setOwner(user);
        return repository.save(video);
    }

}
