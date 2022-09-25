package com.ll.exam.app423.app.security.jwt;

import com.ll.exam.app423.app.member.repository.MemberRepository;
import com.ll.exam.app423.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    public boolean verifyToken(String token) {
        return true;
    }

    public String getUsername(String token) {
        String username = token;
        return username;
    }

    public String getAccessToken(MemberContext memberContext) {
        return memberContext.getUsername();
    }
}
