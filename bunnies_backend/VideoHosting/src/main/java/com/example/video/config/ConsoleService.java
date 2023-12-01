package com.example.video.config;

import org.springframework.stereotype.Service;

@Service("console")
public class ConsoleService {

    public boolean log(Object obj) {
        System.err.println(obj);
        return true;
    }

}
