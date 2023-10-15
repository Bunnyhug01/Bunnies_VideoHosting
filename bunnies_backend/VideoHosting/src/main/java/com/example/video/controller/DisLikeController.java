package com.example.video.controller;

import com.example.video.dto.StatusDTO;
import com.example.video.entity.User;
import com.example.video.service.DisLikeService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("${cross.origin.url}")
@AllArgsConstructor
@RestController
public class DisLikeController {

    private DisLikeService service;

    @PostMapping("/dislikes/{id}")
    public void addLike(@PathVariable Long id, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        service.addDisLike(user.getId(), id);
    }

    @DeleteMapping("/dislikes/{id}")
    public void removeLike(@PathVariable Long id, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        service.removeDisLike(user.getId(), id);
    }

    @GetMapping("/dislikes/{id}")
    public StatusDTO hasLike(@PathVariable Long id, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        return new StatusDTO(service.hasDisLike(user.getId(), id));
    }

}
