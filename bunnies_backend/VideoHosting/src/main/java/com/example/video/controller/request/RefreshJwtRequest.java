package com.example.video.controller.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshJwtRequest {

    public String refreshToken;

}