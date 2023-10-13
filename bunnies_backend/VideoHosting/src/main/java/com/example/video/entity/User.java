package com.example.video.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements BaseEntity, UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToMany
    private Set<Role> roles;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @OneToMany(mappedBy = "owner")
    private Set<Video> videos;
    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToMany
    private Set<Video> likes;
    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToMany
    private Set<Video> dislikes;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}
