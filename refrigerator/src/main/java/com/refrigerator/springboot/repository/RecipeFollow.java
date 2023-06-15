package com.refrigerator.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refrigerator.springboot.entity.Member;
import com.refrigerator.springboot.entity.RecipeBoard;
import com.refrigerator.springboot.entity.RecipeFollowWriting;

public interface RecipeFollow extends JpaRepository<RecipeFollowWriting, Long> {

	@Query(value = "select count(r) from RecipeFollowWriting r where r.member =:member and r.recipeBoard=:recipeBoard", nativeQuery = true)
	public Integer findFollowing(@Param("member") Member member, @Param("recipeBoard") RecipeBoard recipeBoard);

	@Query(value = "select r from RecipeFollowWriting r where r.member =:member and r.recipeBoard=:recipeBoard", nativeQuery = true)
	public RecipeFollowWriting findToDel(@Param("member") Member member, @Param("recipeBoard") RecipeBoard recipeBoard);

}
