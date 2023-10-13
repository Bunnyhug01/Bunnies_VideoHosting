package com.example.video.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Video implements BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 8 * 1024)
    private String detail;

    @Column(nullable = false)
    private String logoUrl;

    @Column(nullable = false)
    private String videoUrl;

    @Column(nullable = false)
    private int likes;

    @Column(nullable = false)
    private int dislikes;

    @Column(nullable = false)
    private int views;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToOne(optional = false)
    private User owner;

}
