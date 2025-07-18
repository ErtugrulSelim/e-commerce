package com.example.eticaret.controller;

import com.example.eticaret.model.User;
import com.example.eticaret.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.save(user);
    }
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
