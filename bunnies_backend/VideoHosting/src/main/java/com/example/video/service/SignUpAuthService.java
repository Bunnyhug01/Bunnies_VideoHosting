package com.example.video.service;

import com.example.video.dto.TokensDTO;
import com.example.video.dto.request.SignInUserRequest;

public interface SignUpAuthService {

    TokensDTO signup(SignInUserRequest data);

}
