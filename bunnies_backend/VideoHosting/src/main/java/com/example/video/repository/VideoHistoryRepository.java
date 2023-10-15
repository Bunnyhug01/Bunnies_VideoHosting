package com.example.video.repository;

import com.example.video.entity.Video;
import com.example.video.entity.VideoHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface VideoHistoryRepository extends JpaRepository<VideoHistory, Long> {

    Collection<VideoHistory> findAllByVideo(Video video);

}
