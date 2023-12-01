package com.example.video.controller;

import com.example.video.controller.annotations.CommentAuthorById;
import com.example.video.dto.request.CommentCreateRequest;
import com.example.video.dto.request.CommentReplaceRequest;
import com.example.video.entity.Comment;
import com.example.video.entity.User;
import com.example.video.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin("${cross.origin.url}")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final Logger LOG = LoggerFactory.getLogger(CommentController.class);

    private final CommentService service;

    @GetMapping("/comments")
    public Collection<? extends Comment> getAll() {
        return service.getAllComments();
    }

    @GetMapping("/comments/{id}")
    public Comment getOne(@PathVariable long id) {
        return service.getOneComment(id);
    }

    @PostMapping("/comments")
    public Comment create(@RequestBody CommentCreateRequest request, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        return service.createComment(request, user);
    }

    @CommentAuthorById
    @DeleteMapping("/comments/{id}")
    public void deleteOne(@PathVariable long id) {
        service.deleteComment(id);
    }

    @CommentAuthorById
    @PutMapping("/comments/{id}")
    public Comment replace(@PathVariable long id, @RequestBody CommentReplaceRequest request) {
        return service.replaceComment(id, request);
    }

}