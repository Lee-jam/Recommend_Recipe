package com.refrigerator.springboot.controller;

import com.refrigerator.springboot.dto.BardDTO;
import com.refrigerator.springboot.service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @Autowired
    RandomService randomService;

    @GetMapping(value = "/")
    public String main(Model model){
        return "main";
    }

    @GetMapping(value = "/random")
    @ResponseBody
    public BardDTO randomRecipe(){
        BardDTO bardDTO = randomService.recipeCreate();

        return bardDTO;
    }
}
