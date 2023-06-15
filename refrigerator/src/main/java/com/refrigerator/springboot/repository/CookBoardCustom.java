package com.refrigerator.springboot.repository;

import com.refrigerator.springboot.dto.CookBoardDTO;
import com.refrigerator.springboot.dto.CookSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CookBoardCustom {

	Page<CookBoardDTO> findByDelCheckIsNAndSearch(CookSearchDTO cookSearchDTO, Pageable pageable);
	
}
