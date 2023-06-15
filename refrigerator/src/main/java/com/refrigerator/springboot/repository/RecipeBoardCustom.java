package com.refrigerator.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refrigerator.springboot.dto.RecipeSearchDTO;
import com.refrigerator.springboot.dto.ShowBoardDTO;
import com.refrigerator.springboot.entity.RecipeBoard;

public interface RecipeBoardCustom {

	Page<ShowBoardDTO> findByDelCheckIsNAndSearch(RecipeSearchDTO recipeSearchDTO, Pageable pageable);

}
