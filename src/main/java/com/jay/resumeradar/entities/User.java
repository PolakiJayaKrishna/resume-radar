package com.jay.resumeradar.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public String getUsername() {
        return email;  //Spring uses email as a username
    }

    // These 4 are just standard "Yes" answers for now
    @Override public boolean isAccountNonExpired() { return true; } //Is your membership still valid, or did it expire last year?
    @Override public boolean isAccountNonLocked() { return true; } //Are you currently banned for fighting?
    @Override public boolean isCredentialsNonExpired() { return true; } //Is your password too old? Do you need to reset it? We are not forcing to Change Password for every 90 Days
    @Override public boolean isEnabled() { return true; } //Have you verified your email address? "YES" for now . In the future, we will Change
}
