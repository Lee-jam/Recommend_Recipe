package com.refrigerator.springboot.dto;

import com.refrigerator.springboot.entity.RecipeBoard;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RecipeContentDTO {
	
	private Long contentId;
	@NotEmpty(message =  "내용 입력 요망")
	private String RecipeContent;
	private RecipeBoard recipeBoard;	
}
