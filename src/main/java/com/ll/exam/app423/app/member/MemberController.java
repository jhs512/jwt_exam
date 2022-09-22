package com.ll.exam.app423.app.member;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    @GetMapping("/myInfo")
    public String myInfo() {
        return "Ok";
    }
}
