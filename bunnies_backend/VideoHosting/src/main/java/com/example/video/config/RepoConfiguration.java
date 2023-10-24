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
        final var maksim = userRepository.findByUsername("Maksim").orElseGet(() -> {
            var m = new User();
            m.setUsername("Maksim");
            m.setLogoUrl("https://lh3.googleusercontent.com/ogw/AKPQZvzOqoDpVzFgDdOWDskd8giBGX2hbQrp85akDisc=s32-c-mo");
            m.setPassword(passwordEncoder.encode("1234"));
            var roles = new HashSet<Role>();
            roles.add(roleRepository.findByAuthority("USER"));
            roles.add(roleRepository.findByAuthority("ADMIN"));
            m.setRoles(roles);
            return userRepository.save(m);
        });
        final var arseny = userRepository.findByUsername("Arseny").orElseGet(() -> {
            var m = new User();
            m.setUsername("Arseny");
            m.setLogoUrl("https://lh3.googleusercontent.com/ogw/AKPQZvzOqoDpVzFgDdOWDskd8giBGX2hbQrp85akDisc=s32-c-mo");
            m.setPassword(passwordEncoder.encode("1234"));
            var roles = new HashSet<Role>();
            roles.add(roleRepository.findByAuthority("USER"));
            roles.add(roleRepository.findByAuthority("ADMIN"));
            m.setRoles(roles);
            return userRepository.save(m);
        });
        var v1 = videoRepository.findByTitle("Название 1").orElseGet(() -> {
            var v = new Video();
            v.setTitle("Название 1");
            v.setDetail("Описание");
            v.setVideoUrl("https://firebasestorage.googleapis.com/v0/b/bunnies-aad60.appspot.com/o/videos%2FTrevor_Something.mp4?alt=media&token=8af7ad29-4187-4601-ad8f-a65a35c18d20");
            v.setLogoUrl("https://firebasestorage.googleapis.com/v0/b/bunnies-aad60.appspot.com/o/images%2FTrevor%20Something.png?alt=media&token=058454ef-f542-4293-bd4d-87263c25e17e");
            v.setOwner(maksim);
            userRepository.save(maksim);
            return videoRepository.save(v);
        });
        var v2 = videoRepository.findByTitle("Название 2").orElseGet(() -> {
            var v = new Video();
            v.setTitle("Название 2");
            v.setDetail("Описание");
            v.setVideoUrl("https://firebasestorage.googleapis.com/v0/b/bunnies-aad60.appspot.com/o/videos%2FTrevor_Something.mp4?alt=media&token=8af7ad29-4187-4601-ad8f-a65a35c18d20");
            v.setLogoUrl("https://firebasestorage.googleapis.com/v0/b/bunnies-aad60.appspot.com/o/images%2FTrevor%20Something.png?alt=media&token=058454ef-f542-4293-bd4d-87263c25e17e");
            v.setOwner(maksim);
            userRepository.save(maksim);
            return videoRepository.save(v);
        });
    }

}
