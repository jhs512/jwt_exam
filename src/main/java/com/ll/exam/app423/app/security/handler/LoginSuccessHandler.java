package com.ll.exam.app423.app.security.handler;

import com.ll.exam.app423.app.member.entity.Member;
import com.ll.exam.app423.app.member.repository.MemberRepository;
import com.ll.exam.app423.app.security.dto.MemberContext;
import com.ll.exam.app423.app.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        MemberContext memberContext = (MemberContext) authentication.getPrincipal();

        String accessToken = jwtProvider.getAccessToken(memberContext);
        Member member = memberRepository.findByUsername(memberContext.getUsername()).get();
        member.setAccessToken(accessToken);

        response.addHeader("Authentication", accessToken);
    }
}
