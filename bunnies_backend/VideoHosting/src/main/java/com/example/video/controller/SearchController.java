package com.example.video.controller;

import com.example.video.entity.Video;
import com.example.video.service.SearchService;
import lombok.AllArgsConstructor;
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

}
