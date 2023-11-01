package com.example.video.service;

import com.example.video.dto.request.CommentRequest;
import com.example.video.dto.request.ReplaceCommentRequest;
import com.example.video.entity.Comment;
import com.example.video.entity.User;

import java.util.Collection;

public interface CommentService {

    Comment createComment(CommentRequest request, User user);

    void deleteComment(long id);

    Comment getOneComment(long id);

    Collection<Comment> getAllComments();

    Comment replaceComment(long id, ReplaceCommentRequest request);

}
