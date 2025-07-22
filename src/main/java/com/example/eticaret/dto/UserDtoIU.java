package com.example.eticaret.dto;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDtoIU {

    private String username;
    private String email;
    private boolean isAdmin;
    private String password;
}
