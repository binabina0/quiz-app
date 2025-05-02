package com.example.quizapp.config;

import com.example.quizapp.enteties.Role;
import com.example.quizapp.enteties.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsDao implements UserDetails {
    private final long id;
    private final String username;
    private final String password;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsDao(User user) {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        email = user.getEmail();
        authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
//        List<Role> roles = user.getRoles();
//        if (roles != null) {
//            authorities = roles
//                .stream()
//                .map(role ->
//                        new SimpleGrantedAuthority(role.name()))
//                .toList();}
//        else {
//            authorities = Collections.emptyList();
//        }
    }

    public long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
