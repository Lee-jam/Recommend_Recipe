package com.refrigerator.springboot.dto;

import lombok.Data;

@Data
public class NotifyingCheckDTO {

	private String member;
	private String notifiedMember;
	private String notifiedCase;
	private String notifiedReason;
	private String title;
	private String content;
	private Long boardId;
	private Long commentId;
	private Long writingId;
	
}
