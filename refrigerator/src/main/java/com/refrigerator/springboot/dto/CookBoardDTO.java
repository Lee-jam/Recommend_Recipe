package com.refrigerator.springboot.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.refrigerator.springboot.entity.CookImage;
import com.refrigerator.springboot.entity.Member;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CookBoardDTO {
	private Long writingId;
	private Integer writingCount;
	private CookImage cookImage;
	private String title;
	private Member member;
	private LocalDateTime regDate;
	private Integer seenCount;
	private Integer recomCount;
	
	@QueryProjection
	public CookBoardDTO(Long writingId, Integer writingCount, CookImage cookImage, String title, Member member,LocalDateTime regDate, Integer seenCount, Integer recomCount) {
		this.writingId = writingId;
		this.writingCount = writingCount;
		this.cookImage = cookImage;
		this.title = title;
		this.member = member;
		this.regDate = regDate;
		this.seenCount = seenCount;
		this.recomCount = recomCount;
	}
}
