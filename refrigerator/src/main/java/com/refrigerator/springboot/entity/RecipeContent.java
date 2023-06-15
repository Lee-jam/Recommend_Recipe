package com.refrigerator.springboot.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "RecipeContent")
@Getter
@Setter
@ToString
public class RecipeContent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long contentid;
	@ManyToOne
	@JoinColumn(name="writingid")
	private RecipeBoard recipeboard;
	private String content;
	private String url;

	public static List<RecipeContent> createContents(String[] recipeContents, RecipeBoard recipeBoard, ArrayList<String>urls){
		List<RecipeContent> createContents = new ArrayList<>();
		for(int i=0; i<recipeContents.length; i++) {
			String recipeContent = recipeContents[i];
			String url = urls.get(i);
			RecipeContent content = new RecipeContent();
			content.setRecipeboard(recipeBoard);
			content.setContent(recipeContent);
			content.setUrl(url);
			createContents.add(content);
		}
		return createContents;
	}

}
