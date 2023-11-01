package com.example.video.dto.request;

import lombok.Data;

@Data
public class CommentRequest {

    String text;
    long videoId;

}
