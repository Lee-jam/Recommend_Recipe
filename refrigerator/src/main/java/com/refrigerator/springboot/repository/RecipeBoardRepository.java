package com.refrigerator.springboot.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refrigerator.springboot.entity.RecipeBoard;

public interface RecipeBoardRepository extends JpaRepository<RecipeBoard, Long>, RecipeBoardCustom{

	@Query("select r from RecipeBoard r where delcheck='N' order by writingid desc")
	Page<RecipeBoard> findByDelCheckIsN(Pageable pageable);

	@Query(value = "select max(writingCount) from RecipeBoard r where delcheck='N'", nativeQuery = true)
	Integer findByAllWriting();

	@Query("select r from RecipeBoard r where r.writingid = :writingid")
	RecipeBoard findByRecipeDetail(@Param("writingid") Long writing);


}