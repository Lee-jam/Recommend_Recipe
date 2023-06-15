package com.refrigerator.springboot.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.refrigerator.springboot.dto.WriteCommentDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "RecipeComment")
@Getter
@Setter
public class RecipeComment {

	@Id
	@GeneratedValue
	private Long commentid;
	@JoinColumn(name = "mem_id")
	@ManyToOne
	private Member member;
	@JoinColumn(name = "writingid")
	@ManyToOne
	private RecipeBoard recipeboard;
	private String content;
	private LocalDateTime regdate;
	@JoinColumn(name = "boardid")
	@ManyToOne
	private Board board;

	public static RecipeComment createRecipeComment(String content, RecipeBoard recipeBoard, Member member, Board board) {
		RecipeComment recipeComment = new RecipeComment();
		recipeComment.setMember(member);
		recipeComment.setRecipeboard(recipeBoard);
		recipeComment.setContent(content);
		recipeComment.setRegdate(LocalDateTime.now());
		recipeComment.setBoard(board);
		return recipeComment;

	}

}
