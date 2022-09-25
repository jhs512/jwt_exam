package com.ll.exam.app423.app.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.exam.app423.app.member.entity.Member;
import com.ll.exam.app423.app.member.repository.MemberRepository;
import com.ll.exam.app423.app.security.dto.MemberContext;
import com.ll.exam.app423.app.security.jwt.JwtProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final ObjectMapper om;
    private final MemberRepository memberRepository;

    public JwtAuthorizationFilter(JwtProvider jwtProvider, ObjectMapper om, MemberRepository memberRepository) {
        this.jwtProvider = jwtProvider;
        this.om = om;
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null) {
            String token = bearerToken.substring("Bearer ".length());

            if (jwtProvider.verifyToken(token)) {
                String username = jwtProvider.getUsername(token);
                Member member = memberRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("'%s' Username not found.".formatted(username)));

                forceAuthentication(member);
            }
        }

        filterChain.doFilter(request, response);
    }

    public void forceAuthentication(Member member) {
        MemberContext memberContext = new MemberContext(member);

        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        memberContext,
                        null,
                        member.getAuthorities()
                );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
