package com.example.quizapp.jwt;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private long expiresIn;
//    private String refreshToken;
    private String username;
}
