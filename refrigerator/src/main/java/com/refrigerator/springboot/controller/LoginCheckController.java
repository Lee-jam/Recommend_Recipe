package com.refrigerator.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginCheckController {

    @ResponseBody
    @GetMapping("/loginCheck")
    public String loginChecking(){
        return "<script>" +
                "alert('로그인 후 이용 가능합니다.');"+
                "history.back();"+
                "</script>";
    }

}
