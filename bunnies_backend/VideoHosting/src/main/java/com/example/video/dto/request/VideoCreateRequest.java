package com.example.video.dto.request;

import lombok.Data;

@Data
public class VideoCreateRequest {

    String title;
    String detail;
    String logoUrl;
    String videoUrl;

    Boolean isPrivate;

}
