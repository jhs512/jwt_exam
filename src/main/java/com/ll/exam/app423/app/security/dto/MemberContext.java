package com.ll.exam.app423.app.security.dto;

import com.ll.exam.app423.app.member.entity.Member;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class MemberContext extends User {
    public MemberContext(Member member, List<SimpleGrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);
    }
}
