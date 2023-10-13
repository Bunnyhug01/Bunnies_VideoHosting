package com.example.video.controller.assembler;

import com.example.video.controller.VideoController;
import com.example.video.entity.Video;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class VideoModelAssembler implements RepresentationModelAssembler<Video, EntityModel<Video>> {

    @Override
    public EntityModel<Video> toModel(Video employee) {
        return EntityModel.of(employee,
                WebMvcLinkBuilder.linkTo(methodOn(VideoController.class).getOne(employee.getId())).withSelfRel(),
                linkTo(methodOn(VideoController.class).getAll()).withRel("all"));
    }

}