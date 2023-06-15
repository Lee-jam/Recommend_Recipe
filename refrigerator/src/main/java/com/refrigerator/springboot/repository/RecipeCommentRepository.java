package com.refrigerator.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refrigerator.springboot.entity.RecipeBoard;
import com.refrigerator.springboot.entity.RecipeComment;

public interface RecipeCommentRepository extends JpaRepository<RecipeComment, Long> {

	@Query(value = "select c from RecipeComment c where c.recipeBoard = :recipeBoard order by commentid asc", nativeQuery = true)
	List<RecipeComment> findRecipeCommentByWritingId(@Param("recipeBoard") RecipeBoard recipeBoard);

	@Query(value = "select c from RecipeComment c where c.recipeBoard = :recipeBoard and c.commentid =:commentid", nativeQuery = true)
	RecipeComment findCommentByRecipeAndCommentId(@Param("recipeBoard") RecipeBoard recipeboard, @Param("commentid") Long commentid);

	@Query(value = "select c from RecipeComment c where c.commentid =:commentid",nativeQuery = true)
	RecipeComment findCommentByCommentId(@Param("commentid") Long commentid);
}
