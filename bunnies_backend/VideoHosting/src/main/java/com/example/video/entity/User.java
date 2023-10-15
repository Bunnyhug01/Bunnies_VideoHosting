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
import java.util.Objects;
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

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
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
    
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<VideoHistory> history;
    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToMany
    private Set<User> subscribers;
    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToMany(mappedBy = "subscribers")
    private Set<User> subscriptions;

    @Column(nullable = false)
    private String logoUrl;

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

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public String toString() {
        String sb = "User{" + "id=" + id +
                '}';
        return sb;
    }

    public boolean hasRole(String role) {
        return roles.stream().anyMatch(x -> x.getAuthority().equals("ROLE_" + x));
    }

}
