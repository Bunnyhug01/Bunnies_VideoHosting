package com.example.video.controller;

import com.example.video.config.ConsoleService;
import com.example.video.config.MethodSecurityConfig;
import com.example.video.config.SecurityConfig;
import com.example.video.entity.Role;
import com.example.video.entity.User;
import com.example.video.entity.Video;
import com.example.video.security.JwtProvider;
import com.example.video.security.JwtTokenAuthenticationFilter;
import com.example.video.service.UserService;
import com.example.video.service.VideoService;
import com.example.video.service.impl.AuthServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {
        VideoController.class,
        AuthenticationController.class,
        JwtProvider.class,
        SecurityConfig.class,
        MethodSecurityConfig.class,
        AuthServiceImpl.class,
        ConsoleService.class,
})
public class VideoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @MockBean
    private VideoService service;

    @MockBean
    private UserService userService;

    @Test
    void getOne() throws Exception {
        var user = User.builder().id(0L).username("Michail").password("1234").roles(Set.of()).build();
        var owner = User.builder().id(1L).username("Michail").password("1234").roles(Set.of()).build();
        var video = Video.builder().id(0L).owner(owner).title("Title").isPrivate(false).build();
        Mockito.when(userService.getOneUser(user.getId())).thenReturn(user);
        Mockito.when(userService.getOneUser(owner.getId())).thenReturn(owner);
        Mockito.when(service.getOneVideo(video.getId())).thenReturn(video);
        var token = jwtProvider.generateAccessToken(user);
        mvc.perform(get("/videos/{id}", video.getId())
                        .header(HttpHeaders.AUTHORIZATION, JwtTokenAuthenticationFilter.HEADER_PREFIX + token))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(video)));
    }

    @Test
    void getOnePrivateOk() throws Exception {
        var userA = User.builder().id(1L).username("Michail A").password("1234").roles(Set.of()).build();
        var video = Video.builder().id(1L).title("Title").owner(userA).isPrivate(true).build();
        Mockito.when(userService.getOneUser(userA.getId())).thenReturn(userA);
        Mockito.when(service.getOneVideo(video.getId())).thenReturn(video);
        var token = jwtProvider.generateAccessToken(userA);
        mvc.perform(get("/videos/{id}", video.getId())
                        .header(HttpHeaders.AUTHORIZATION, JwtTokenAuthenticationFilter.HEADER_PREFIX + token))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(video)));
    }

    @Test
    void getOnePrivate() throws Exception {
        var userA = User.builder().id(1L).username("Michail A").password("1234").roles(Set.of()).build();
        var userB = User.builder().id(2L).username("Michail B").password("1234").roles(Set.of()).build();
        var video = Video.builder().id(1L).title("Title").owner(userA).isPrivate(true).build();
        Mockito.when(userService.getOneUser(userA.getId())).thenReturn(userA);
        Mockito.when(userService.getOneUser(userB.getId())).thenReturn(userB);
        Mockito.when(service.getOneVideo(video.getId())).thenReturn(video);
        var token = jwtProvider.generateAccessToken(userB);
        mvc.perform(get("/videos/{id}", video.getId())
                        .header(HttpHeaders.AUTHORIZATION, JwtTokenAuthenticationFilter.HEADER_PREFIX + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void getOnePrivateAdmin() throws Exception {
        var userA = User.builder().id(1L).username("Michail A").password("1234").roles(Set.of()).build();
        var userB = User.builder().id(2L).username("Michail B").password("1234").roles(Set.of(new Role("ADMIN"))).build();
        var video = Video.builder().id(1L).title("Title").owner(userA).isPrivate(true).build();
        Mockito.when(userService.getOneUser(userA.getId())).thenReturn(userA);
        Mockito.when(userService.getOneUser(userB.getId())).thenReturn(userB);
        Mockito.when(service.getOneVideo(video.getId())).thenReturn(video);
        var token = jwtProvider.generateAccessToken(userB);
        mvc.perform(get("/videos/{id}", video.getId())
                        .header(HttpHeaders.AUTHORIZATION, JwtTokenAuthenticationFilter.HEADER_PREFIX + token))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(video)));
    }

}