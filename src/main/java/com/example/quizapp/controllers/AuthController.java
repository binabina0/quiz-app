package com.example.quizapp.controllers;


import com.example.quizapp.jwt.JwtResponse;
import com.example.quizapp.jwt.RefreshTokenService;
import com.example.quizapp.payload.LoginRequest;
import com.example.quizapp.payload.RegisterRequest;
import com.example.quizapp.services.AuthService;
import com.example.quizapp.jwt.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    AuthenticationManager authenticationManager;
    RefreshTokenService refreshTokenService;
    JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody LoginRequest loginRequest)  {
        return authService.login(loginRequest);
    }
}
