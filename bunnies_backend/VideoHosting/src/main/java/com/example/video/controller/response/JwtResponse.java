package com.example.video.controller.response;

import com.example.video.security.JwtTokenAuthenticationFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class JwtResponse {

    private String type;
    private String access;

    public JwtResponse(String access) {
        this(JwtTokenAuthenticationFilter.BEARER, access);
    }

}