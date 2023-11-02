package com.example.video.service.impl;

import com.example.video.controller.advice.exception.CommentNotFoundException;
import com.example.video.dto.request.CommentRequest;
import com.example.video.dto.request.ReplaceCommentRequest;
import com.example.video.entity.Comment;
import com.example.video.entity.User;
import com.example.video.repository.CommentRepository;
import com.example.video.service.CommentService;
import com.example.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    private final VideoService service;

    @Override
    public Comment createComment(CommentRequest request, User user) {
        return repository.save(Comment.builder()
                .text(request.getText())
                .author(user)
                .video(service.getById(request.getVideoId()))
                .build());
    }

    @Override
    public void deleteComment(long id) {
        repository.deleteById(id);
    }

    @Override
    public Comment getOneComment(long id) {
        return repository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Override
    public Collection<Comment> getAllComments() {
        return repository.findAll();
    }

    @Override
    public Comment replaceComment(long id, ReplaceCommentRequest request) {
        var comment = getOneComment(id);
        var text = request.getText();
        if (text != null)
            comment.setText(text);
        return repository.save(comment);
    }

}
