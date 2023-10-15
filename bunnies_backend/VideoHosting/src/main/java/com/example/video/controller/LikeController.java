package com.example.video.controller;

import com.example.video.dto.StatusDTO;
import com.example.video.entity.User;
import com.example.video.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("${cross.origin.url}")
@AllArgsConstructor
@RestController
public class LikeController {

    private LikeService service;

    @PostMapping("/likes/{id}")
    public void addLike(@PathVariable Long id, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        service.addLike(user.getId(), id);
    }

    @DeleteMapping("/likes/{id}")
    public void removeLike(@PathVariable Long id, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        service.removeLike(user.getId(), id);
    }

    @GetMapping("/likes/{id}")
    public StatusDTO hasLike(@PathVariable Long id, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        return new StatusDTO(service.hasLike(user.getId(), id));
    }

}
