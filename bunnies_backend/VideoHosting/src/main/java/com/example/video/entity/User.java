package com.example.video.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @OneToMany(mappedBy = "owner")
    private Collection<Video> videos;
    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @OneToMany
    private Collection<Video> likes;
    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @OneToMany
    private Collection<Video> dislikes;

}
