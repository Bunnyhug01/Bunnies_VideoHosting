package com.example.video.controller;

import com.example.video.controller.advice.UserNotFoundException;
import com.example.video.controller.assembler.UserModelAssembler;
import com.example.video.entity.User;
import com.example.video.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@CrossOrigin("${cross.origin.url}")
@AllArgsConstructor
@RestController
public class UserController {

    private final UserRepository repository;
    private final UserModelAssembler assembler;

    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> getAll(Principal principal) {
        System.out.println(principal);
        var users = repository.findAll().stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAll(null)).withSelfRel());
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> getOne(@PathVariable Long id) {
        var user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return assembler.toModel(user);
    }

}
