package com.example.video.repository;

import com.example.video.entity.Comment;
import com.example.video.entity.User;
import com.example.video.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentByAuthorAndVideo(User author, Video video);

}
