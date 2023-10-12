package com.example.video.controller;

import com.example.video.entity.Video;
import com.example.video.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@CrossOrigin("${cross.origin.url}")
@AllArgsConstructor
@RestController
public class VideoController {

    private final VideoRepository repository;
    private final VideoModelAssembler assembler;

    @GetMapping("/videos")
    public CollectionModel<EntityModel<Video>> getAll() {
        var videos = repository.findAll().stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(videos, linkTo(methodOn(VideoController.class).getAll()).withSelfRel());
    }

    @GetMapping("/videos/{id}")
    public EntityModel<Video> getOne(@PathVariable Long id) {
        var video = repository.findById(id).orElseThrow(() -> new VideoNotFoundException(id));
        return assembler.toModel(video);
    }

    @DeleteMapping("/videos/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        var video = repository.findById(id).orElseThrow(() -> new VideoNotFoundException(id));
        repository.delete(video);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/videos")
    public Video create(@RequestBody Video video) {
        return repository.save(video);
    }

    @PutMapping("/videos/{id}")
    public Video replace(@RequestBody Video newVideo, @PathVariable Long id) {
        return repository.findById(id)
                .map(video -> {
                    video.setVideoUrl(newVideo.getVideoUrl());
                    video.setLogoUrl(newVideo.getLogoUrl());
                    video.setDetail(newVideo.getDetail());
                    video.setTitle(newVideo.getTitle());
                    video.setDetail(newVideo.getDetail());
                    return repository.save(video);
                })
                .orElseThrow(() -> new VideoNotFoundException(id));
    }

}
