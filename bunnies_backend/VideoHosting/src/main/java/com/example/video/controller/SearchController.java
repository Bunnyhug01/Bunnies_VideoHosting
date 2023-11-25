package com.example.video.controller;

import com.example.video.entity.User;
import com.example.video.entity.Video;
import com.example.video.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("${cross.origin.url}")
@AllArgsConstructor
@RestController
public class SearchController {

    private final SearchService service;

    @GetMapping("/videos/search/name/{name}")
    public List<Video> searchVideoByName(@PathVariable String name) {
        return service.searchVideoByName(name);
    }

    @GetMapping("/videos/search/like/{name}")
    public List<Video> searchLikedVideoByName(@PathVariable String name, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        return service.searchLikedVideoByName(name, user);
    }

}
