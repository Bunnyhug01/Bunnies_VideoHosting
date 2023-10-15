package com.example.video.config;

import com.example.video.entity.Role;
import com.example.video.entity.User;
import com.example.video.entity.Video;
import com.example.video.repository.RoleRepository;
import com.example.video.repository.UserRepository;
import com.example.video.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
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
    @EventListener(ApplicationReadyEvent.class)
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
        final var maksim = userRepository.findByUsername("Maksim").orElseGet(() -> {
            var m = new User();
            m.setUsername("Maksim");
            m.setPassword(passwordEncoder.encode("1234"));
            var roles = new HashSet<Role>();
            roles.add(roleRepository.findByAuthority("USER"));
            roles.add(roleRepository.findByAuthority("ADMIN"));
            roles.add(roleRepository.findByAuthority("MODERATOR"));
            m.setRoles(roles);
            return userRepository.save(m);
        });
        final var arseny = userRepository.findByUsername("Arseny").orElseGet(() -> {
            var m = new User();
            m.setUsername("Arseny");
            m.setPassword(passwordEncoder.encode("1234"));
            var roles = new HashSet<Role>();
            roles.add(roleRepository.findByAuthority("USER"));
            roles.add(roleRepository.findByAuthority("ADMIN"));
            roles.add(roleRepository.findByAuthority("MODERATOR"));
            m.setRoles(roles);
            return userRepository.save(m);
        });
        videoRepository.deleteAll();
        var v1 = new Video();
        v1.setTitle("ПИОНЕРЫ-ПОПАДАНЦЫ | Советский исекай в анимации");
        v1.setDetail("Если вы хоть немного увлекаетесь японской анимацией, то наверняка слышали термин «исекай». Это один из самых популярных жанров аниме, если по-простому, — истории о попаданцах. Когда герои магических образом перемещаются в другие миры — обычно после того, как их сбивает грузовик. \n");
        v1.setVideoUrl("https://youtu.be/kYFkWip40C8");
        v1.setLogoUrl("https://i.ytimg.com/vi/kYFkWip40C8/hqdefault.jpg?sqp=-oaymwEcCPYBEIoBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLCmWEy9t6N_cAH3plY0kv-UGO7MJw");
        v1.setOwner(maksim);
        userRepository.save(maksim);
        videoRepository.save(v1);
    }

}
