package com.tamil.ecommerce_backend.controller;

import com.tamil.ecommerce_backend.dto.*;
import com.tamil.ecommerce_backend.model.User;
import com.tamil.ecommerce_backend.repository.UserRepository;
import com.tamil.ecommerce_backend.service.AuthService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService,
                          UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh-token")
    public RefreshResponse refresh(@RequestBody RefreshRequest request) {
        return authService.refresh(request.getRefreshToken());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public java.util.List<User> getAllUsers() {
        return userRepository.findAll();
    }

}