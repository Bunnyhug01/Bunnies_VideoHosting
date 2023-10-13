package com.example.video.config;

import com.example.video.entity.User;
import com.example.video.entity.Video;
import com.example.video.repository.UserRepository;
import com.example.video.repository.VideoRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class RepoConfiguration {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;

    @PostConstruct
    void init() {
        videoRepository.deleteAll();
        userRepository.deleteAll();
        var u1 = new User();
        u1.setName("Maksim");
        u1.setPassword("1234");
        userRepository.save(u1);
        var v = new Video();
        v.setTitle("ПИОНЕРЫ-ПОПАДАНЦЫ | Советский исекай в анимации");
        v.setDetail("Если вы хоть немного увлекаетесь японской анимацией, то наверняка слышали термин «исекай». Это один из самых популярных жанров аниме, если по-простому, — истории о попаданцах. Когда герои магических образом перемещаются в другие миры — обычно после того, как их сбивает грузовик. \n");
        v.setVideoUrl("https://youtu.be/kYFkWip40C8");
        v.setLogoUrl("https://i.ytimg.com/vi/kYFkWip40C8/hqdefault.jpg?sqp=-oaymwEcCPYBEIoBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLCmWEy9t6N_cAH3plY0kv-UGO7MJw");
        v.setOwner(u1);
        videoRepository.save(v);
    }

}
