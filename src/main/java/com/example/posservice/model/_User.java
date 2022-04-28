package com.example.posservice.model;

import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class _User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    @NotBlank(message = "Email cannot be empty")
    @Email
    private String email;
    private Instant created;
    private boolean enabled;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<_Authority> authorities = new ArrayList<>();

    public void addAuthority(_Authority authority) {
        authority.setUsername(username);
        authority.setUser(this);
        this.authorities.add(authority);
    }

    public List<SimpleGrantedAuthority> getSimpleGrantedAuthorities() {
        return this.authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }

    public List<_Authority> getUserAuthorities(Set<SimpleGrantedAuthority> authorities) {

        return authorities.stream()
                .map(simpleGrantedAuthority -> mapToAuthority(simpleGrantedAuthority.getAuthority()))
                .collect(Collectors.toList());
    }

    public _Authority mapToAuthority(String authority) {
        return _Authority.builder()
                .authority(authority)
                .username(this.username)
                .user(this)
                .build();
    }

}
