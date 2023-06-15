package com.refrigerator.springboot.dto;

import com.refrigerator.springboot.entity.RecipeBoard;
import com.refrigerator.springboot.entity.RecipeContent;
import com.refrigerator.springboot.entity.RecipeImage;
import lombok.Data;

import java.util.List;

@Data
public class RewriteDTO {

	private RecipeBoard recipeBoard;
	List<RecipeContent> recipeContents;
	List<RecipeImage> recipeImages;
	
}
