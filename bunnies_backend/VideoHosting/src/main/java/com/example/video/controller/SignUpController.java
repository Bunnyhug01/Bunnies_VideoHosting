package com.example.video.controller;

import com.example.video.controller.advice.exception.NotHaveRefreshTokenException;
import com.example.video.controller.request.JwtRequest;
import com.example.video.controller.response.JwtResponse;
import com.example.video.dto.TokensDTO;
import com.example.video.service.AuthService;
import com.example.video.service.SignUpAuthService;
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
public class SignUpController {

    private static final String REFRESH_TOKEN = "refresh_token";
    private final Logger LOG = LoggerFactory.getLogger(SignUpController.class);

    private final SignUpAuthService signUpAuthService;

    private JwtResponse getJwtResponse(HttpServletResponse response, TokensDTO tokens) {
        var access_token = tokens.getAccessToken();
        var refresh_token = tokens.getRefreshToken();
        var cookie = new Cookie(REFRESH_TOKEN, refresh_token);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/auth");
        response.addCookie(cookie);
        return new JwtResponse(access_token);
    }

    @PostMapping("/auth/base/signup")
    public JwtResponse signup(@RequestBody JwtRequest request, HttpServletResponse response) {
        LOG.debug("signup");
        var tokens = signUpAuthService.signup(request);
        return getJwtResponse(response, tokens);
    }

}