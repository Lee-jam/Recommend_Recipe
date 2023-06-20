package com.refrigerator.springboot.controller;

import com.refrigerator.springboot.constant.LoginType;
import com.refrigerator.springboot.dto.MemberDto;
import com.refrigerator.springboot.entity.Member;
import com.refrigerator.springboot.repository.MemberRepository;
import com.refrigerator.springboot.repository.SocialRepository;
import com.refrigerator.springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final PasswordEncoder encoder;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final SocialRepository socialRepository;
    private final HttpSession httpSession;
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
    @GetMapping(value = "/member/selectAuth")
    public String emailChkForm() {
        return "/auth/selectAuth";
    }
    @RequestMapping(value = "/member/idChk", method = {RequestMethod.POST})
    public @ResponseBody void memberChk(@RequestBody String email, HttpServletResponse res, HttpServletRequest req, Model model) throws IOException {
        boolean usableEmail = false;

        String[] split = email.split("%40");
        String str = split[1].replace("=", "");
        String changeStr = split[0] + "@" + str;
        try {
            String chkID = memberRepository.findByEmailAndLoginType(changeStr, LoginType.NOMAL).getEmail();
            if (chkID.equals(changeStr)) {
                usableEmail = false;
            }
        } catch (Exception e) {
            usableEmail = true;
        }

        //Json 방식으로 데이터를 만들고, [ usableEmail : value ] 로 만들어서 값 전달 후 ajax 스크립트가 동작하면서 [ usableEmail : value ] 를 인식하고 value 값에 따라서 동작
        //json 객체생성
        JSONObject jso = new JSONObject();
        //boolean 값 넣기
        jso.put("usableEmail", usableEmail);

        // 콘텐트 타입 utf-8로 지정(json은 맵 형태처럼 key, value 값을 받는대 object를 받을수 없어 직접 타입 설정)
        res.setContentType("text/html;charset=utf-8");

        //값 출력
        PrintWriter out = res.getWriter();
        //json 형태로 전달
        out.print(jso.toString());

    }

    @RequestMapping(value = "/member/nickChk", method = {RequestMethod.POST})
    public @ResponseBody void memberNickChk(@RequestBody String nickname, HttpServletResponse res, HttpServletRequest req, Model model) throws IOException {
        boolean result = false;
        String replaceStr = nickname.replace("=", "");
        String trimStr = replaceStr.trim();
        String str = trimStr.replace(" ", "");
        try {
            String chkNick = memberRepository.findByNickname(str).getNickname();
            if (chkNick.equals(str)) {
                result = false;
            }
        } catch (Exception e) {
            result = true;
        }

        JSONObject jso = new JSONObject();
        jso.put("result", result);

        res.setContentType("text/html;charset=utf-8");
        PrintWriter out = res.getWriter();
        out.print(jso.toString());
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
    @GetMapping(value = "/member/findPW")
    public String findPasswordPage() {
        return "/login/find/findPw";
    }

    @GetMapping(value = "/member/findEmail")
    public String findEmailPage() {
        return "/login/find/findEmail";
    }
}
