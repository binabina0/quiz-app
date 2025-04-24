package com.example.quizapp.services;

import com.example.quizapp.dao.UserDao;
import com.example.quizapp.enteties.User;
import com.example.quizapp.payload.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    UserDao userDao;
    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        if(userDao.findByUsername(registerRequest.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username is already in use", HttpStatus.BAD_REQUEST);
        }
        if(userDao.findByEmail(registerRequest.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email is already in use", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        user.setEnabled(true);
        userDao.save(user);
        return new ResponseEntity<>("User successfully registered", HttpStatus.CREATED);
    }
}
