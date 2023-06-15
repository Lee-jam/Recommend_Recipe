package com.refrigerator.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.refrigerator.springboot.entity.CookBoard;
import com.refrigerator.springboot.entity.CookRecommend;
import com.refrigerator.springboot.entity.Member;

public interface CookRecommendRepository extends JpaRepository<CookRecommend, Long>{

	@Query("select count(c) from CookRecommend c where c.cookboard = :cookboard and c.member =:member")
	int checkingRecommend(@Param("cookboard") CookBoard cookboard, @Param("member") Member member);

	@Query("select count(c) from CookRecommend c where c.cookboard = :cookboard")
	int countRecom(@Param("cookboard") CookBoard cookboard);

}
