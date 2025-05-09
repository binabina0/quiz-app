package com.example.quizapp.services;

import com.example.quizapp.config.UserDetailsDao;
import com.example.quizapp.config.UserServiceImpl;
import com.example.quizapp.dao.UserDao;
import com.example.quizapp.payload.JwtResponse;
import com.example.quizapp.enteties.Role;
import com.example.quizapp.enteties.User;
import com.example.quizapp.jwt.JwtService;
import com.example.quizapp.payload.LoginRequest;
import com.example.quizapp.payload.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDao userDao;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserServiceImpl userDetailsService;

    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        if(userDao.findByUsername(registerRequest.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username is already in use", HttpStatus.BAD_REQUEST);
        }
        if(userDao.findByEmail(registerRequest.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email is already in use", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setRoles(List.of(Role.USER));
        user.setEnabled(true);
        userDao.save(user);

        return new ResponseEntity<>("User successfully registered", HttpStatus.CREATED);
    }

    public ResponseEntity<JwtResponse> login(LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsDao userDetails = (UserDetailsDao)  authentication.getPrincipal();
            String jwt = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);
            userDao.updateRefreshToken(userDetails.getUsername(), refreshToken);
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setToken(jwt);
            jwtResponse.setRefreshToken(refreshToken);
            jwtResponse.setUsername(userDetails.getUsername());
            jwtResponse.setExpiresIn(jwtService.getExpirationTime());
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public Map<String, String> refreshToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        String accessToken;
        if(username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtService.isTokenValid(refreshToken, userDetails)) {
                accessToken = jwtService.generateToken(userDetails);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                return tokens;
            }

        }
        throw new RuntimeException("Invalid refresh token");




    }
}
