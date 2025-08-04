package com.example.eticaret.controller;

import com.example.eticaret.dto.UserDto;
import com.example.eticaret.model.User;
import com.example.eticaret.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody User user) {
        return userService.login(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
       return userService.deleteById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody User user) {
        return userService.update(user);
    }
}