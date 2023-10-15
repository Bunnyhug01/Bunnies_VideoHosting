package com.example.video.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class StatusDTO {

    public String status;

    public StatusDTO(boolean status) {
        this(status + "");
    }

}
