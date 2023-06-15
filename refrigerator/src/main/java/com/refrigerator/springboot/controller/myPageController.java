package com.refrigerator.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/mypage")
public class myPageController {
    @GetMapping("/")
    public String mypage01(Principal principal){
        //principal
        return "mypage/mypage01";
    }
}
