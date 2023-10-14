package com.example.video.config;

import com.example.video.entity.Role;
import com.example.video.entity.User;
import com.example.video.entity.Video;
import com.example.video.repository.RoleRepository;
import com.example.video.repository.UserRepository;
import com.example.video.repository.VideoRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@AllArgsConstructor
@Configuration
public class RepoConfiguration {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @PostConstruct
    void init() {
        if (roleRepository.findByAuthority("USER") == null) {
            var r = new Role();
            r.setAuthority("USER");
            roleRepository.save(r);
        }
        if (roleRepository.findByAuthority("ADMIN") == null) {
            var r = new Role();
            r.setAuthority("ADMIN");
            roleRepository.save(r);
        }
        if (roleRepository.findByAuthority("MODERATOR") == null) {
            var r = new Role();
            r.setAuthority("MODERATOR");
            roleRepository.save(r);
        }
        videoRepository.deleteAll();
        userRepository.deleteAll();
        var u1 = new User();
        u1.setUsername("Maksim");
        u1.setPassword(passwordEncoder.encode("1234"));
        var roles = new HashSet<Role>();
        roles.add(roleRepository.findByAuthority("USER"));
        roles.add(roleRepository.findByAuthority("ADMIN"));
        roles.add(roleRepository.findByAuthority("MODERATOR"));
        u1.setRoles(roles);
        userRepository.save(u1);
        var u2 = new User();
        u2.setUsername("Arseny");
        u2.setPassword(passwordEncoder.encode("1234"));
        var roles2 = new HashSet<Role>();
        roles2.add(roleRepository.findByAuthority("USER"));
        roles2.add(roleRepository.findByAuthority("ADMIN"));
        roles2.add(roleRepository.findByAuthority("MODERATOR"));
        u2.setRoles(roles2);
        userRepository.save(u2);
        var v = new Video();
        v.setTitle("ПИОНЕРЫ-ПОПАДАНЦЫ | Советский исекай в анимации");
        v.setDetail("Если вы хоть немного увлекаетесь японской анимацией, то наверняка слышали термин «исекай». Это один из самых популярных жанров аниме, если по-простому, — истории о попаданцах. Когда герои магических образом перемещаются в другие миры — обычно после того, как их сбивает грузовик. \n");
        v.setVideoUrl("https://youtu.be/kYFkWip40C8");
        v.setLogoUrl("https://i.ytimg.com/vi/kYFkWip40C8/hqdefault.jpg?sqp=-oaymwEcCPYBEIoBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLCmWEy9t6N_cAH3plY0kv-UGO7MJw");
        v.setOwner(u2);
        videoRepository.save(v);
    }

}
