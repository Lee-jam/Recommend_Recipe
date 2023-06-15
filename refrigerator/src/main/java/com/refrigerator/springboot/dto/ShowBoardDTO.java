package com.refrigerator.springboot.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import com.refrigerator.springboot.entity.Member;
import com.refrigerator.springboot.entity.RecipeImage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowBoardDTO {

	private Long writingId;
	private Integer writingCount;
	private RecipeImage recipeImage;
	private String title;
	private String difficulty;
	private Member member;
	private LocalDateTime regDate;
	private Integer seenCount;
	private Integer recomCount;

	@QueryProjection
	public ShowBoardDTO(Long writingId, Integer writingCount, RecipeImage recipeImage, String title,String difficulty, Member member,LocalDateTime regDate, Integer seenCount, Integer recomCount) {

		this.writingId=writingId;
		this.writingCount=writingCount;
		this.recipeImage=recipeImage;
		this.title=title;
		this.difficulty=difficulty;
		this.member=member;
		this.regDate = regDate;
		this.seenCount=seenCount;
		this.recomCount=recomCount;

	}
}

