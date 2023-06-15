package com.refrigerator.springboot.dto;

import lombok.Data;

@Data
public class CookSearchDTO {

	private String searchBy;
	private String searchQuery="";
	
}
