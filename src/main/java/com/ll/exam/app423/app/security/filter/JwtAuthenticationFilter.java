package com.ll.exam.app423.app.security.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.exam.app423.app.security.handler.LoginFailHandler;
import com.ll.exam.app423.app.security.handler.LoginSuccessHandler;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper om;

    public JwtAuthenticationFilter(ObjectMapper om, AuthenticationManager authenticationManager, LoginSuccessHandler loginSuccessHandler, LoginFailHandler loginFailHandler) {
        super(new AntPathRequestMatcher("/member/login",
                "POST"), authenticationManager);
        this.om = om;
        setAuthenticationSuccessHandler(loginSuccessHandler);
        setAuthenticationFailureHandler(loginFailHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        Map<String, String> body = getBody(request);
        String username = body.get("username");
        String password = body.get("password");

        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username,
                password);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private Map<String, String> getBody(HttpServletRequest request) {
        try {
            return om.readValue(request.getInputStream(), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

