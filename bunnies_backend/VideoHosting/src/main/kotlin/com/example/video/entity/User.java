package com.example.video.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
@Entity
public class User implements BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @OneToMany
    private Collection<Video> videos;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToMany
    private Collection<Video> like;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToMany
    private Collection<Video> dislike;

}
