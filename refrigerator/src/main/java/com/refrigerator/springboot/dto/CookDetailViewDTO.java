package com.refrigerator.springboot.dto;

import com.refrigerator.springboot.entity.CookBoard;
import com.refrigerator.springboot.entity.CookComment;
import com.refrigerator.springboot.entity.CookImage;
import lombok.Data;

import java.util.List;

@Data
public class CookDetailViewDTO {
	private CookBoard cookBoard;
	private List<CookImage> cookImage;
	private List<CookComment> cookComment;
}
