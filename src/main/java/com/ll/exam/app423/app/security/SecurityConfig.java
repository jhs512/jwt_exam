package com.ll.exam.app423.app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.exam.app423.app.security.filter.JwtAuthenticationFilter;
import com.ll.exam.app423.app.security.handler.LoginFailHandler;
import com.ll.exam.app423.app.security.handler.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final ObjectMapper om;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailHandler loginFailHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(STATELESS)
                )
                .addFilterBefore(new JwtAuthenticationFilter(
                                om,
                                authenticationConfiguration.getAuthenticationManager(),
                                loginSuccessHandler,
                                loginFailHandler
                        ),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

