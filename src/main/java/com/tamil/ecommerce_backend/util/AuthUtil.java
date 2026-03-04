package com.tamil.ecommerce_backend.util;

import com.tamil.ecommerce_backend.model.User;
import com.tamil.ecommerce_backend.repository.UserRepository;
import com.tamil.ecommerce_backend.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthUtil {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthUtil(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    // Extract logged-in user
    public User getLoggedInUser(String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token missing");
        }

        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserIdFromAccess(token);

        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")
                );
    }

    // Check admin
    public void validateAdmin(String authHeader) {

        User user = getLoggedInUser(authHeader);

        String role = user.getRole() != null ? user.getRole().toUpperCase() : "";

        if (!role.equals("ADMIN") && !role.equals("ROLE_ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin only access");
        }
    }
}