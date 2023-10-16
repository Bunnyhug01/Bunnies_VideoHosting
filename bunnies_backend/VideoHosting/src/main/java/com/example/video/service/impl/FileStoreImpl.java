package com.example.video.service.impl;

import com.example.video.service.FileStore;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileStoreImpl implements FileStore {

    private final File dir = new File("uploaded");

    @Override
    public File getFile(String name) {
        return new File(dir, name);
    }

}
