package com.example.video.service;

import com.example.video.dto.request.CommentCreateRequest;
import com.example.video.dto.request.CommentReplaceRequest;
import com.example.video.entity.Comment;
import com.example.video.entity.User;

import java.util.Collection;

public interface CommentService {

    Comment createComment(CommentCreateRequest request, User author);

    void deleteComment(long id);

    Comment getOneComment(long id);

    Collection<Comment> getAllComments();

    Comment replaceComment(long id, CommentReplaceRequest request);

}
