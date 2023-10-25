package com.example.video.controller;

import com.example.video.controller.advice.exception.NotHaveRefreshTokenException;
import com.example.video.controller.request.JwtRequest;
import com.example.video.controller.response.JwtResponse;
import com.example.video.dto.TokensDTO;
import com.example.video.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@CrossOrigin("${cross.origin.url}")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private static final String REFRESH_TOKEN = "refresh_token";
    private final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthService service;

    @PostMapping("/auth/refreshtoken")
    public JwtResponse refreshToken(HttpServletRequest request) {
        LOG.debug("refreshToken");
        var cookies = request.getCookies();
        if (cookies == null)
            throw new NotHaveRefreshTokenException();
        var refresh_token = Arrays.stream(cookies).filter(x -> x.getName().equals(REFRESH_TOKEN)).findAny()
                .orElseThrow(NotHaveRefreshTokenException::new).getValue();
        var access_token = service.refreshToken(refresh_token);
        return new JwtResponse(access_token);
    }

    @PostMapping("/auth/base/signin")
    public JwtResponse signin(@RequestBody JwtRequest request, HttpServletResponse response) {
        LOG.debug("signin");
        var tokens = service.signin(request);
        return getJwtResponse(response, tokens);
    }

    private JwtResponse getJwtResponse(HttpServletResponse response, TokensDTO tokens) {
        var access_token = tokens.getAccessToken();
        var refresh_token = tokens.getRefreshToken();
        var cookie = new Cookie(REFRESH_TOKEN, refresh_token);
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
        cookie.setPath("/auth");
        response.addCookie(cookie);
        return new JwtResponse(access_token);
    }

    @PostMapping("/auth/logout")
    public void logout(HttpServletResponse response) {
        LOG.debug("logout");
        var cookie = new Cookie(REFRESH_TOKEN, "");
        cookie.setMaxAge(0);
//        cookie.setSecure(true);
//        cookie.setHttpOnly(true);
        cookie.setPath("/auth");
        response.addCookie(cookie);
    }

    @PostMapping("/auth/base/signup")
    public JwtResponse signup(@RequestBody JwtRequest request, HttpServletResponse response) {
        LOG.debug("signup");
        var tokens = service.signup(request);
        return getJwtResponse(response, tokens);
    }

}