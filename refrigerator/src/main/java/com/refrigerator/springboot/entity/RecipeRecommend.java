package com.refrigerator.springboot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "RecipeRecommend")
@Getter
@Setter
@ToString
public class RecipeRecommend {

	@Id
	@GeneratedValue
	private Long recomid;
	@ManyToOne
	@JoinColumn(name = "mem_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name= "writingid")
	private RecipeBoard recipeboard;

	public static RecipeRecommend updateRecommend(RecipeBoard recipeBoard, Member member) {
		RecipeRecommend recipeRecommend = new RecipeRecommend();
		recipeRecommend.setMember(member);
		recipeRecommend.setRecipeboard(recipeBoard);
		return recipeRecommend;
	}

}
