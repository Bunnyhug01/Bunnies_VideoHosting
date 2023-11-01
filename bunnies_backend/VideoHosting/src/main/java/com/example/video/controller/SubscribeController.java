package com.example.video.controller;

import com.example.video.dto.response.StatusResponse;
import com.example.video.entity.User;
import com.example.video.service.SubscribeService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("${cross.origin.url}")
@AllArgsConstructor
@RestController
public class SubscribeController {

    private SubscribeService service;

    @PostMapping("/subscribes/{id}")
    public void addSubscribe(@PathVariable Long id, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        service.addSubscribe(user.getId(), id);
    }

    @DeleteMapping("/subscribes/{id}")
    public void removeSubscribe(@PathVariable Long id, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        service.removeSubscribe(user.getId(), id);
    }

    @GetMapping("/subscribes/{id}")
    public StatusResponse hasSubscribe(@PathVariable Long id, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        return new StatusResponse(service.hasSubscribe(user.getId(), id));
    }

}
