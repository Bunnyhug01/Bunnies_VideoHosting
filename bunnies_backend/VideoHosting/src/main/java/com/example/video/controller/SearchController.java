package com.example.video.controller;

import com.example.video.controller.annotations.NotReturnIfPrivateById;
import com.example.video.entity.User;
import com.example.video.entity.Video;
import com.example.video.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("${cross.origin.url}")
@AllArgsConstructor
@RestController
public class SearchController {

    private final SearchService service;

    @NotReturnIfPrivateById
    @GetMapping("/videos/search/name/{name}")
    public List<Video> searchVideoByName(@PathVariable String name) {
        return new ArrayList<>(service.searchVideoByName(name));
    }

    @NotReturnIfPrivateById
    @GetMapping("/videos/search/like/{name}")
    public List<Video> searchLikedVideoByName(@PathVariable String name, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        return new ArrayList<>(service.searchLikedVideoByName(name, user));
    }

    @NotReturnIfPrivateById
    @GetMapping("/videos/search/history/{name}")
    public List<Video> searchVideoByNameInHistory(@PathVariable String name, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        return new ArrayList<>(service.searchVideoByNameInHistory(name, user));
    }

    @NotReturnIfPrivateById
    @GetMapping("/videos/search/owner/{name}")
    public List<Video> searchVideoByOwner(@PathVariable String name, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        return new ArrayList<>(service.searchVideoByOwner(name, user));
    }

}
