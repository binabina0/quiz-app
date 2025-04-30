package com.example.quizapp.dao;

import com.example.quizapp.enteties.Role;
import com.example.quizapp.enteties.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDao implements UserDetails {
    private long id;
    private String username;
    private String password;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsDao(User user) {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        email = user.getEmail();
        List<Role> roles = user.getRoles();
        if (roles != null) {
            authorities = roles
                .stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.toString()))
                .toList();}
        else {
            authorities = Collections.emptyList();
        }
    }
}
