package com.example.video.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoHistory {

    @JsonIgnore
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Date date;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToOne
    private Video video;

    @Column(nullable = false)
    private float viewed = 0;

}
