package com.example.video.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Video implements BaseEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2 * 1024)
    private String detail;

    @Column(nullable = false, length = 2 * 1024)
    private String logoUrl;

    @Column(nullable = false, length = 2 * 1024)
    private String videoUrl;

    @Column(nullable = false)
    private Date uploadDate;

    @Column(nullable = false)
    private int likes;

    @Column(nullable = false)
    private int dislikes;

    @Column(nullable = false)
    private Boolean isPrivate;

    @Column(nullable = false)
    private int views;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @OneToMany(mappedBy = "video", fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return Objects.equals(id, video.id);
    }

    @Override
    public String toString() {
        String sb = "Video{" + "id=" + id +
                '}';
        return sb;
    }

}
