package com.example.video.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
public class VideoReplaceRequest {

    String title;
    String detail;
    String logoUrl;
    String videoUrl;

    Boolean isPrivate;

}
