package com.example.quizapp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private long expiresIn;
    private String refreshToken;
    private String username;

    public JwtResponse(String token) {
        this.token = token;
    }
}
