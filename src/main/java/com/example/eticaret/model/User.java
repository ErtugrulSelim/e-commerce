package com.example.eticaret.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name="users")
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private boolean isAdmin;
}

