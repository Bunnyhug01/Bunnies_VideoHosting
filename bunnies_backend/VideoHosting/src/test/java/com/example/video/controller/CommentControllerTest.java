package com.example.video.controller;

import com.example.video.controller.advice.exception.CommentNotFoundException;
import com.example.video.entity.Comment;
import com.example.video.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService service;

    @Test
    void getOne() throws Exception {
        var comment = Comment.builder().text("TEXT").build();
        var id = 1L;
        Mockito.when(service.getOneComment(id)).thenReturn(comment);
        mvc.perform(get("/comments/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(comment)));
    }

    @Test
    void getOneNotFound() throws Exception {
        var id = 1L;
        Mockito.when(service.getOneComment(id)).thenThrow(new CommentNotFoundException(id));
        mvc.perform(get("/comments/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll() throws Exception {
        var comment = Comment.builder().text("TEXT").build();
        Mockito.when(service.getAllComments()).thenReturn(List.of(comment));
        mvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(comment))));
    }

}