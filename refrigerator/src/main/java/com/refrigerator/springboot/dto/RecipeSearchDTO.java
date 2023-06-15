package com.refrigerator.springboot.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RecipeSearchDTO {
	
	private String searchBy;
	private String searchQuery="";

}
