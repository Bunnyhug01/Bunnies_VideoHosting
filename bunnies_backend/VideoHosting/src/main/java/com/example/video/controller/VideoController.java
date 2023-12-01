package com.example.video.controller;

import com.example.video.controller.annotations.NotReturnIfPrivateById;
import com.example.video.controller.annotations.VideoOwnerById;
import com.example.video.controller.annotations.VideoOwnerIfPrivateByResult;
import com.example.video.dto.request.VideoCreateRequest;
import com.example.video.dto.request.VideoReplaceRequest;
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

    @NotReturnIfPrivateById
    @GetMapping("/videos")
    public Collection<Video> getAll() {
        return service.getAllVideos();
    }

    @VideoOwnerIfPrivateByResult
    @GetMapping("/videos/{id}")
    public Video getOne(@PathVariable long id) {
        return service.getOneVideo(id);
    }

    @VideoOwnerById
    @DeleteMapping("/videos/{id}")
    public void deleteOne(@PathVariable long id) {
        service.deleteVideo(id);
    }

    @PostMapping("/videos")
    public Video create(@RequestBody VideoCreateRequest video, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        return service.createVideo(video, user.getId());
    }

    @VideoOwnerById
    @PutMapping("/videos/{id}")
    public Video replace(@PathVariable long id, @RequestBody VideoReplaceRequest video) {
        return service.replaceVideo(id, video);
    }

}
