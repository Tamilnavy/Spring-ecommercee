package com.tamil.ecommerce_backend.service;

import com.tamil.ecommerce_backend.dto.*;
import com.tamil.ecommerce_backend.model.User;
import com.tamil.ecommerce_backend.repository.UserRepository;
import com.tamil.ecommerce_backend.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String register(RegisterRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Please request OTP first"));

        if (!request.getOtp().equals(user.getOtp()))
            throw new RuntimeException("Invalid OTP");

        if (user.getOtpExpires().isBefore(LocalDateTime.now()))
            throw new RuntimeException("OTP expired");

        user.setPassword(encoder.encode(request.getPassword()));

        // Enforce all new registrations to be normal users
        user.setRole("ROLE_USER");

        user.setPhone(request.getPhone());

        user.setOtp(null);
        user.setOtpExpires(null);

        userRepository.save(user);

        return "User registered successfully";
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid password");

        String token = jwtUtil.generateAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new LoginResponse(token, refreshToken);
    }

    public RefreshResponse refresh(String refreshToken) {

        String userId = jwtUtil.extractUserIdFromRefresh(refreshToken);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (!refreshToken.equals(user.getRefreshToken()))
            throw new RuntimeException("Invalid refresh token");

        String newAccessToken =
                jwtUtil.generateAccessToken(user.getId(), user.getRole());

        return new RefreshResponse(newAccessToken);
    }
}