package com.ecommerce.controller;

import com.ecommerce.dto.*;
import com.ecommerce.model.User;
import com.ecommerce.security.JwtUtil;
import com.ecommerce.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        HashSet<String> roles = new HashSet<>(request.getRoles());
        User user = userService.register(request.getUsername(), request.getPassword(), roles);
        return "User registered: " + user.getUsername();
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        try {
            // Tenta autenticar o usuário
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Se passar, gera token JWT
            User user = userService.findByUsername(request.getUsername());
            String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
            return new JwtResponse(token);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
