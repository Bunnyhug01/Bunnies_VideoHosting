package com.example.video.service;

import com.example.video.controller.request.JwtRequest;
import com.example.video.dto.TokensDTO;

public interface AuthService {

    String refreshToken(String refreshToken);

    TokensDTO signin(JwtRequest request);

    TokensDTO signup(JwtRequest data);

}
