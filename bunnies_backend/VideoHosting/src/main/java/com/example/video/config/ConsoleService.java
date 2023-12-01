package com.example.video.config;

import org.springframework.stereotype.Service;

@Service("console")
public class ConsoleService {

    public boolean log(Long id) {
        System.out.println(id);
        return false;
    }

}
