package com.refrigerator.springboot.dto;

import com.refrigerator.springboot.entity.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WriteCommentDTO {
	private String email;
	private String content;
	private Long writingId;
	private Board board;
}
