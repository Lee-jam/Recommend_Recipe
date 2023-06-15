package com.refrigerator.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refrigerator.springboot.entity.RecipeBoard;
import com.refrigerator.springboot.entity.RecipeImage;

public interface RecipeImageRepository extends JpaRepository<RecipeImage, Long> {

	@Query("select i from RecipeImage i where recipeBoard = :recipeBoard")
	List<RecipeImage>findRecipeImagesByWritingId(@Param("recipeBoard") RecipeBoard recipeBoard);

	@Query("select i from RecipeImage i where recipeBoard =:recipeBoard and repimage='Y'")
	RecipeImage findRepImage(@Param("recipeBoard")RecipeBoard recipeBoard);

}
