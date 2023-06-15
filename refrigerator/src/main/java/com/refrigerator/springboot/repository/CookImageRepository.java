package com.refrigerator.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refrigerator.springboot.entity.CookBoard;
import com.refrigerator.springboot.entity.CookImage;

public interface CookImageRepository extends JpaRepository<CookImage, Long> {

    @Query("select c from CookImage c where c.cookboard=:cookboard")
    List<CookImage> findByCookBoard(@Param("cookboard")CookBoard cookboard);

    @Query("select c from CookImage c where c.cookboard=:cookboard and c.repimage='Y'")
    CookImage findRepImage(@Param("cookboard")CookBoard cookboard);

}
