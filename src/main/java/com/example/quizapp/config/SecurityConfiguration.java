package com.example.quizapp.config;

//import com.example.quizapp.controllers.OAuthController;
import com.example.quizapp.jwt.JwtAuthenticationFilter;
import com.example.quizapp.oAuth2.CustomOAuth2UserService;
import com.example.quizapp.oAuth2.OAuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;


//    public SecurityConfiguration(
//            JwtAuthenticationFilter jwtFilter,
//            AuthenticationProvider authenticationProvider,
//            OAuthSuccessHandler oAuthSuccessHandler,
//            CustomOAuth2UserService customOAuth2UserService
//    ) {
//        this.authenticationProvider = authenticationProvider;
//        this.jwtAuthenticationFilter = jwtFilter;
//        this.oAuthSuccessHandler = oAuthSuccessHandler;
//        this.customOAuth2UserService = customOAuth2UserService;
//    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/oauth/**").permitAll()
                    .requestMatchers("/quiz/**", "/question/**").hasRole("ADMIN")
                    .requestMatchers("quiz/get/**").hasAnyRole("ADMIN", "USER")
                    .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
//                    .defaultSuccessUrl("/user", true)
                            .successHandler(oAuthSuccessHandler)
                            .userInfoEndpoint(userInfo -> userInfo
                                    .userService(customOAuth2UserService))
            )

            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
