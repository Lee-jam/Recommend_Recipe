package com.refrigerator.springboot.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.refrigerator.springboot.entity.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotifyListDTO {

	private Long notifingId;
	private int notifiedNum;
	private Member member;
	private String notifiedMember;
	private String title;
	private String notifiedContent;
	private String notifiedCase;
	private String notifiedReason;
	private LocalDateTime regDate;
	private LocalDateTime didDate;
	private String did;
	private Long writingId;
	private Long commentId;
	private Long boardId;
	
	@QueryProjection
	public NotifyListDTO(Long notifingId, int notifiedNum, Member member, String notifiedMember, String title, String notifiedContent, String notifiedCase, String notifiedReason, LocalDateTime regDate, LocalDateTime didDate, String did, Long writingId, Long commentId, Long boardId) {
		this.notifingId=notifingId;
		this.notifiedNum=notifiedNum;
		this.member=member;
		this.notifiedMember=notifiedMember;
		this.title=title;
		this.notifiedContent=notifiedContent;
		this.notifiedCase=notifiedCase;
		this.notifiedReason=notifiedReason;
		this.regDate=regDate;
		this.didDate=didDate;
		this.did=did;
		this.writingId=writingId;
		this.commentId=commentId;
		this.boardId=boardId;
	}
	
}
