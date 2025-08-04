package com.example.eticaret.service;

import com.example.eticaret.exceptions.AlreadyExistException;
import com.example.eticaret.exceptions.NotFoundException;
import com.example.eticaret.model.User;
import com.example.eticaret.dto.UserDto;
import com.example.eticaret.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    private UserDto convertoDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertoDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<String> register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new AlreadyExistException("User already exist");
        }
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<String> login(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());

        User dbuser = optionalUser.get();
        String token = jwtUtil.generateToken(dbuser);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteById(Long id) {
        if (id == null) {
            throw new NotFoundException("Id not found");
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> update(User user) {
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
    }

}

