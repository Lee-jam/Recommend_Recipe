package com.refrigerator.springboot.controller;

import com.refrigerator.springboot.dto.BlogDTO;
import com.refrigerator.springboot.dto.RecipeApiDTO;
import com.refrigerator.springboot.dto.VideoDTO;
import com.refrigerator.springboot.service.RecipeApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/refrigeratorRecipe")
public class RecipeController {
    private final RecipeApiService recipeApiService;

    @GetMapping(value ="/")
    public String recipe(Principal principal, Model model){

        if(principal==null){
            return "redirect:/loginCheck";
        }
        else {
            List<RecipeApiDTO> recipeLists = recipeApiService.getRecipeData();
            model.addAttribute("recipeLists",recipeLists);
            return "refrigeratorRecipe/recipe";
        }
    }
    @GetMapping("/blog")
    @ResponseBody
    public List<BlogDTO> blogRecipe(String recipeName){
        System.out.println(recipeName);
        List<BlogDTO> blogList =recipeApiService.searchBlogRecipe(recipeName);
        return blogList;
    }
    @GetMapping("/video")
    @ResponseBody
    public List<VideoDTO> videoRecipe(String recipeName){
        System.out.println(recipeName);
        List<VideoDTO> videoList =recipeApiService.searchvideoRecipe(recipeName);
        return videoList;
    }
    @GetMapping("/choice")
    @ResponseBody
    public RecipeApiDTO RecipeDetail(String recipeName){
        System.out.println(recipeName);
        RecipeApiDTO recipeDetails =recipeApiService.recipeDetails(recipeName);
        return recipeDetails;
    }

    @GetMapping("/api/recipe/set")
    @ResponseBody
    public String recipeSet(){
        recipeApiService.recipeApiSave();
        return "sucess";
    }
}
