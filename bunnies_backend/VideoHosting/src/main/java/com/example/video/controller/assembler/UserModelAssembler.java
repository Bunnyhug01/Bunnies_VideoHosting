package com.example.video.controller.assembler;

import com.example.video.controller.UserController;
import com.example.video.entity.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User user) {
        return EntityModel.of(user,
                WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getOne(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getAll(null)).withRel("all"));
    }

}