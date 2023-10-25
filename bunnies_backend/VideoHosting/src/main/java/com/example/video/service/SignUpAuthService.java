package com.example.video.service;

import com.example.video.controller.request.JwtRequest;
import com.example.video.dto.TokensDTO;

public interface SignUpAuthService {

    TokensDTO signup(JwtRequest data);

}
