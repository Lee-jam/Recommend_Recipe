package com.refrigerator.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refrigerator.springboot.entity.RecipeBoard;
import com.refrigerator.springboot.entity.RecipeContent;

public interface RecipeContentRepository extends JpaRepository<RecipeContent, Long>{

	@Query("select r from RecipeContent r where recipeBoard = :recipeBoard")
	List<RecipeContent> findByRecipeContent(@Param("recipeBoard") RecipeBoard recipeBoard);

}
