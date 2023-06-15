package com.refrigerator.springboot.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "Ingredient")
@Getter
@Setter
@ToString
public class Ingredient {
	
	@Id
	private Long ingid;
	@JoinColumn(name = "writingid")
	@ManyToOne
	private RecipeBoard recipeboard;
	private String tagname;
}
