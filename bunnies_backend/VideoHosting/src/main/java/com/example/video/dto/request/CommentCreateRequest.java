package com.example.video.dto.request;

import lombok.Data;

@Data
public class CommentCreateRequest {

    String text;
    long videoId;

}
