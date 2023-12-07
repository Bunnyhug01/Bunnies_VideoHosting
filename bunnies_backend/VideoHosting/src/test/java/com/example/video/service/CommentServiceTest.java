package com.example.video.service;

import com.example.video.controller.advice.exception.CommentNotFoundException;
import com.example.video.dto.request.CommentCreateRequest;
import com.example.video.dto.request.CommentReplaceRequest;
import com.example.video.entity.Comment;
import com.example.video.entity.User;
import com.example.video.entity.Video;
import com.example.video.repository.CommentRepository;
import com.example.video.repository.VideoRepository;
import com.example.video.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {
        CommentServiceImpl.class
})
class CommentServiceTest {

    @Autowired
    private CommentService service;

    @MockBean
    private VideoRepository videoRepository;

    @MockBean
    private CommentRepository repository;

    @Test
    void getOneComment() {
        var comment = Comment.builder().id(2L).text("Hi").build();
        Mockito.when(repository.findById(comment.getId())).thenReturn(Optional.of(comment));
        var result = service.getOneComment(comment.getId());
        assertSame(result, comment);
    }

    @Test
    void getOneCommentNotFount() {
        assertThrows(CommentNotFoundException.class, () -> {
            service.getOneComment(3L);
        });
    }

    @Test
    void getAllComments() {
        var comment = Comment.builder().id(2L).text("Hi").build();
        Mockito.when(repository.findAll()).thenReturn(List.of(comment));
        var result = service.getAllComments();
        assertEquals(result, List.of(comment));
    }

    @Test
    void deleteComment() {
        service.deleteComment(3L);
        Mockito.verify(repository).deleteById(3L);
    }

    @Test
    void createComment() {
        var author = User.builder().build();
        var request = new CommentCreateRequest();
        var video = Video.builder().id(2L).build();
        var result = Comment.builder().text("Hi").video(video).author(author).build();
        request.setText("Hi");
        request.setVideoId(video.getId());
        Mockito.when(videoRepository.getReferenceById(video.getId())).thenReturn(video);
        service.createComment(request, author);
        Mockito.verify(repository).save(result);
    }

    @Test
    void createCommentText() {
        var author = User.builder().build();
        var video = Video.builder().id(2L).build();
        var comment = Comment.builder().id(2L).text("Bad video").video(video).author(author).build();
        var result = Comment.builder().id(2L).text("Good video").video(video).author(author).build();
        Mockito.when(repository.findById(comment.getId())).thenReturn(Optional.of(comment));
        Mockito.when(repository.save(result)).thenReturn(result);
        var request = new CommentReplaceRequest();
        request.setText(result.getText());
        var newComment = service.replaceComment(comment.getId(), request);
        assertEquals(newComment, result);
    }

}