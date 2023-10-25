package com.example.video.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokensDTO {

    private String accessToken;
    private String refreshToken;

}
