package com.refrigerator.springboot.repository;

import com.refrigerator.springboot.entity.RecipeAPI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RecipeApiRepository extends JpaRepository<RecipeAPI,Long> {
    @Query(value="select r.* from recipe_api r where r.rep_nm=:rep_nm", nativeQuery = true)
    RecipeAPI getRecipefindByName(@Param("rep_nm") String recipeName);
}
