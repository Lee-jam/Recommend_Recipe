package com.refrigerator.springboot.dto;

import lombok.Data;

@Data
public class MemBannDTO {

	private String memMail;
	private Long writingId;
	private Long commentId;
	private Long boardId;
	private String selectDate;
	
}
