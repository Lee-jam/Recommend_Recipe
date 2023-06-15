package com.refrigerator.springboot.dto;

import lombok.Data;

@Data
public class NotifyingDTO {

	private String title;
	private String content;
	private Long writingId;
	private Long commentId;
	private String memMail;
	private String memNick;
	private Long boardId;
	
}
