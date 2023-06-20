package com.refrigerator.springboot.controller;

import com.refrigerator.springboot.service.MailService;
import com.refrigerator.springboot.service.PhoneNumberService;
import com.refrigerator.springboot.service.MailService;
import com.refrigerator.springboot.service.PhoneNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final MailService mailService;
    private final PhoneNumberService phoneService;

    @GetMapping(value = "/member/emailChk")
    public String moveEmailChk() {
        return "/auth/emailCheck";
    }

    @RequestMapping(value = "/member/mailChk", method = {RequestMethod.POST})
    @ResponseBody
    public String mailConfirm(@RequestBody String email) throws Exception {
        String[] split = email.split("%40");
        String str = split[1].replace("=", "");
        String changeStr = split[0] + "@" + str;
        String code = mailService.sendMail(changeStr);

        System.out.println("인증코드 : " + code);
        return code;
    }

    @GetMapping(value = "/member/phoneNumChk")
    public String movePhoneChk() {
        return "/auth/phoneCheck";
    }

    @RequestMapping(value = "/member/phoneChk", method = {RequestMethod.POST})
    @ResponseBody
    public String phoneConfirm(@RequestBody String phoneNum) throws Exception {
        String replaceStr = phoneNum.replace("=", "");
        String code = phoneService.sendOne(replaceStr);
        System.out.println("controller : " + code);
        return code;
    }

    @GetMapping("../member/new")
    public String test() {
        return "/member/createForm";
    }
}
