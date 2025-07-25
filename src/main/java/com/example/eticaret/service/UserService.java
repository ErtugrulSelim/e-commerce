package com.example.eticaret.service;

import com.example.eticaret.model.User;
import com.example.eticaret.dto.UserDto;
import com.example.eticaret.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.eticaret.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> {
            UserDto dto = new UserDto();
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            return dto;
        }).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


    public ResponseEntity<?> register(User user) {
        try {
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                return new ResponseEntity<>("User already exist", HttpStatus.CONFLICT);
            }
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal error!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> login(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
            if (optionalUser.isEmpty()) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
            User dbuser = optionalUser.get();

            String token = jwtUtil.generateToken(dbuser);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        catch (AuthenticationException e) {
            System.out.println("Authentication başarısız: " + e.getMessage());
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

    }
    public User update(User user) {
        return userRepository.save(user);
    }

//    public String register(User user) {
//        userRepository.save(user);
//        return "User registered successfully";
//    }
//
//    public String login(User user) {
//        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
//        if (foundUser.isEmpty()) {
//            throw new RuntimeException("Kullanıcı bulunamadı!");
//        }
//        User dbUser = foundUser.get();
//        if (!user.getPassword().equals(dbUser.getPassword())) {
//            throw new RuntimeException("Şifre hatalı!");
//        } else {
//            return null;
//        }
//
//    }
}
