package com.refrigerator.springboot.dto;

import com.refrigerator.springboot.constant.NoticeCheck;
import com.refrigerator.springboot.entity.Board;
import com.refrigerator.springboot.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class WriteFormDTO {
	@NotBlank(message = "제목 입력 요함")
	private String title;
	@NotNull(message = "칼로리 입력 요함")
	private int kcal;
	@NotBlank(message = "선택 요함")
	private String difficulty;
	@NotEmpty(message = "내용 입력 요함")
	private String recipeContent;
	private NoticeCheck noticeCheck;	
	private Board board;
	private Member member;

}
