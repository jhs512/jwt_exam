package com.ll.exam.app423.app.security.service;

import com.ll.exam.app423.app.member.entity.Member;
import com.ll.exam.app423.app.member.repository.MemberRepository;
import com.ll.exam.app423.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("'%s' Username not found.".formatted(username)));

        return new MemberContext(member);
    }
}
