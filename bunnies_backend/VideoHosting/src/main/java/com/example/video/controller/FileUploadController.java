package com.example.video.controller;

import com.example.video.dto.StringDTO;
import com.example.video.entity.User;
import com.example.video.service.FileStore;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@AllArgsConstructor
@RestController
public class FileUploadController {

    private final FileStore tempDir;

    @PostMapping("/upload")
    public StringDTO handleFileUpload(@RequestParam("name") String name, @RequestParam("type") String type, @RequestParam("file") MultipartFile file, Authentication authorization) throws IOException {
        var user = (User) authorization.getPrincipal();
        var n = user.getId() + "_" + name;
        var dir = tempDir.getFile(n);
        dir.getParentFile().mkdirs();
        var bytes = file.getBytes();
        try (var out = new BufferedOutputStream(new FileOutputStream(dir))) {
            out.write(bytes);
        }
        return new StringDTO("/load/" + n + "?type=" + type);
    }

}