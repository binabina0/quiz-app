package com.example.quizapp.oAuth2;

import com.example.quizapp.dao.UserDao;
import com.example.quizapp.jwt.JwtService;
import com.example.quizapp.config.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final UserDao userDao;
    private final JwtService jwtService;
    private final UserServiceImpl userServiceImpl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        UserDetails user = userServiceImpl.loadUserByEmail(email);
        String token = jwtService.generateToken(user);
        response.sendRedirect("api/auth/oauth-success?token=" + token);
    }
}
