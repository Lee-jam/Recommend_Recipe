package com.refrigerator.springboot.repository;

import com.refrigerator.springboot.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngedientRepository extends JpaRepository<Ingredient, Long>{

}
