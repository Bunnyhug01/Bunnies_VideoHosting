package com.example.video.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
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

}
