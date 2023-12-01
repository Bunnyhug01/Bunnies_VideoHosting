package com.example.video.controller.annotations;

import org.springframework.security.access.prepost.PostAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
//@PostAuthorize("@console.log(returnObject.isPrivate == false)")
@PostAuthorize("hasRole('ROLE_ADMIN') or returnObject.isPrivate == false or returnObject.owner.id == authentication.principal.id")
public @interface VideoOwnerIfPrivateByResult {

}
