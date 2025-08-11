package com.example.eticaret.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    @Email()
    private String email;

    private String password;

    private boolean isAdmin;

    @OneToOne
    private Cart cart;

    @Column()
    private long money;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (isAdmin()) {
            return Collections.singleton((new SimpleGrantedAuthority("ROLE_ADMIN")));
        } else {
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

}

