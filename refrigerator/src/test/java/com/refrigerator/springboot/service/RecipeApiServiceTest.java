package com.refrigerator.springboot.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RecipeApiServiceTest {
    @Autowired
    RecipeApiService recipeApiService;

    @Test
    @Transactional
    @DisplayName("블로그 검색API 서비스 테스트")
    void searchBlogRecipe() {
        recipeApiService.searchBlogRecipe("김치찌개");
    }
}