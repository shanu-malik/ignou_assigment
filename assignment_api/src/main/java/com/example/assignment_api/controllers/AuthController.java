package com.example.assignment_api.controllers;

import com.example.assignment_api.core.utils.ApiResponse;
import com.example.assignment_api.dto.UserLoginDto;
import com.example.assignment_api.dto.UserRegistrationDTO;
import com.example.assignment_api.entities.UserEntity;
import com.example.assignment_api.repositories.UserRepository;
import com.example.assignment_api.security.JwtUtil;


import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Map<String, String>>> register(@Valid @RequestBody UserRegistrationDTO dto) {
        try {

            UserEntity dbUser = userRepository
            .findByEnrollmentNumber(dto.getEnrollment_no())
            .orElse(null);

            if (dbUser != null) {
                throw new RuntimeException("Enrollment number already exists");
            }

            dbUser = userRepository
            .findByEmail(dto.getEmail())
            .orElse(null);

            if (dbUser != null) {
                throw new RuntimeException("Email already exists");
            }

            dbUser = userRepository
            .findByMobile(dto.getMobile())
            .orElse(null);

            if (dbUser != null) {
                throw new RuntimeException("Mobile already exists");
            }

            UserEntity user = new UserEntity();
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setEnrollmentNumber(dto.getEnrollment_no());
            user.setMobile(dto.getMobile());
            user.setAddress(dto.getAddress());
            user.setStudyCentreCode(dto.getStudy_centre_code());
            userRepository.save(user);
            ApiResponse<Map<String, String>> response = new ApiResponse<>(true, "Register successfully", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Map<String, String>> response = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@Valid @RequestBody UserLoginDto dto) {
        try {
            UserEntity dbUser = userRepository
            .findByEnrollmentNumber(dto.getEnrollment_no())
            .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(dto.getPassword(), dbUser.getPassword())) {
                throw new RuntimeException("Invalid password");
            }

            String token = jwtUtil.generateToken(dbUser.getEnrollmentNumber());
            
            dbUser.setToken(token); // make sure UserEntity has a field 'private String token;'

            // Update the entity in the database
            userRepository.save(dbUser);

            Map<String, String> tokenMap = Map.of("token", token, "name", dbUser.getName(),"id",dbUser.getId().toString());
            
            ApiResponse<Map<String, String>> response = new ApiResponse<>(true, "Login successful", tokenMap);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            ApiResponse<Map<String, String>> response = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}