package com.refrigerator.springboot.dto;

import lombok.Data;

@Data
public class DoNotifyingDTO {

	private String member;
	private String title;
	private String content;
	private String why;
	private String reason;
	private Long writingId;
	private Long commentId;
	private Long boardId;
	
}
