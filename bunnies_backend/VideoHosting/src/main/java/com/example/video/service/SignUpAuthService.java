package com.example.video.service;

import com.example.video.dto.TokensDTO;
import com.example.video.dto.request.JwtRequest;

public interface SignUpAuthService {

    TokensDTO signup(JwtRequest data);

}
