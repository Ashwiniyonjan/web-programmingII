package com.example.controller;

import com.example.security.JwtUtil;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {

        String loginId = body.get("email");
        if (loginId == null) {
            loginId = body.get("username");
        }
        String password = body.get("password");

        // STEP 1: Try authenticate
        boolean isValid = userService.authenticate(loginId, password);

        if (!isValid) {
            logger.warn("Login failed for user: {}", loginId);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Invalid credentials");

            return ResponseEntity.status(401).body(response);
        }

        // STEP 2: Generate token
        String token = jwtUtil.generateToken(loginId);

        logger.info("Login success for user: {}", loginId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}