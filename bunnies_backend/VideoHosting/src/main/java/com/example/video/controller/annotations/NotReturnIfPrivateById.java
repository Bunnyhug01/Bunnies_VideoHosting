package com.example.video.controller.annotations;

import org.springframework.security.access.prepost.PostFilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PostFilter("hasRole('ROLE_ADMIN') or !filterObject.isPrivate or filterObject.owner.id == authentication.principal.id")
public @interface NotReturnIfPrivateById {

}
