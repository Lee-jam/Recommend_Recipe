package com.refrigerator.springboot.repository;

import com.refrigerator.springboot.entity.RecipeAPI;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecipeApiRepository extends JpaRepository<RecipeAPI,Long> {

}
