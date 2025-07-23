package com.example.eticaret.controller;

import com.example.eticaret.dto.UserDto;
import com.example.eticaret.model.User;
import com.example.eticaret.security.JwtUtil;
import com.example.eticaret.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
    @GetMapping
    public List<UserDto> getAllUsers() {

        return userService.getAllUsers();
    }
    @PostMapping ("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        return userService.login(user);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable Long id) {

        userService.deleteById(id);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        User saved = userService.update(user);
        return ResponseEntity.ok(saved);
    }
    //    @PostMapping("/register")
//    public String register(@RequestBody User user) {
//        return userService.register(user);
//    }
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody User user) {
//        String user1 = userService.login(user);
//        return ResponseEntity.ok(user1);
//     }
}