package com.refrigerator.springboot.controller;

import com.refrigerator.springboot.dto.MemberDto;
import com.refrigerator.springboot.entity.Member;
import com.refrigerator.springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final PasswordEncoder encoder;
    private final MemberService memberService;

    @GetMapping(value = "/member/new")
    public String memberForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "login/createForm";
    }

    @PostMapping(value = "/member/new")
    public String newMember(MemberDto memberFormDto, Model model) {
        try {
            Member member = Member.createMember(memberFormDto, encoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/member/login/createForm";
        }
        return "login/loginForm";
    }

    @GetMapping(value = "/member/login")
    public String loginPage() {

        return "login/loginForm";
    }

    @GetMapping(value = "/member/logout")
    public String logoutMember() {

        return "main";
    }

    @GetMapping(value = "/member/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "login/loginForm";
    }
}
