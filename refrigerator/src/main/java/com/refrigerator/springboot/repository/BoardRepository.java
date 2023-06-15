package com.refrigerator.springboot.repository;

import com.refrigerator.springboot.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
	
	Board findByBoardid(Long id);
	
}
