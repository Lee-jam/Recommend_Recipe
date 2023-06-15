package com.refrigerator.springboot.repository;

import com.refrigerator.springboot.dto.NotifyListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotifyCustom {
	
	Page<NotifyListDTO> getNotifyList(Pageable pageable);

}
