package com.refrigerator.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refrigerator.springboot.entity.CookBoard;

public interface CookBoardRepository extends JpaRepository<CookBoard, Long>, CookBoardCustom {

	@Query("select c from CookBoard c where delCheck ='N'")
	CookBoard findByDelCheckIsN();

	@Query("select count(c) from CookBoard c where delCheck ='N'")
	Integer findByAllWriting();

	@Query("select c from CookBoard c where c.writingid=:writingid")
	CookBoard findByWritingId(@Param("writingid")Long writingid);

}
