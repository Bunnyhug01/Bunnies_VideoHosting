package com.example.video.dto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class StatusResponse {

    public String status;

    public StatusResponse(boolean status) {
        this(status + "");
    }

}
