package com.example.quizapp.services;

import com.example.quizapp.config.UserDetailsDao;
import com.example.quizapp.dao.UserDao;
import com.example.quizapp.jwt.JwtResponse;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDao userDao;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setToken(jwt);
            jwtResponse.setExpiresIn(jwtService.getExpirationTime());
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
}
