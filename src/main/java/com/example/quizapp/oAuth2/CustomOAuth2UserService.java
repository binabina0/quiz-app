package com.example.quizapp.oAuth2;

import com.example.quizapp.dao.UserDao;
import com.example.quizapp.enteties.Role;
import com.example.quizapp.enteties.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserDao userDao;

    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");

        User user = userDao.findByEmail(email)
                .orElseGet(() -> registerUser(attributes));
        return UserPrincipal.create(user, attributes);
    }

    public User registerUser(Map<String, Object> attributes) {
        User user = new User();
        user.setEmail((String) attributes.get("email"));
        user.setUsername((String) attributes.get("name"));
        user.setPassword(UUID.randomUUID().toString());
        user.setRoles(List.of(Role.USER));
        user.setEnabled(true);
        user.setAuthProvider(User.AuthProvider.GOOGLE);
        return userDao.save(user);
    }

}
