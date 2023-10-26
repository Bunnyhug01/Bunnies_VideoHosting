package com.example.video.service;

import com.example.video.dto.TokensDTO;
import com.example.video.dto.request.JwtRequest;

public interface AuthService {

    String refreshToken(String refreshToken);

    TokensDTO signin(JwtRequest request);

}
