package com.refrigerator.springboot.entity;

import java.time.LocalDateTime;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.refrigerator.springboot.dto.DoNotifyingDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "notify")
@Getter
@Setter
@ToString
public class Notify {

	@Id
	@GeneratedValue
	private Long notifingid;
	private int notifiednum;
	@ManyToOne
	@JoinColumn(name="mem_id")
	private Member member;
	private String notifiedmember;
	private String title;
	private String notifiedcontent;
	private String notifiedcase;
	private String notifiedreason;
	private LocalDateTime regdate;
	private LocalDateTime diddate;
	private String did;
	private Long writingid;
	private Long commentid;
	private Long boardid;

	public static Notify createNotifying(DoNotifyingDTO doNotifyingDTO, int notifiedNum, Member member) {
		Notify notify = new Notify();
		notify.setMember(member);
		notify.setNotifiednum(notifiedNum);
		notify.setNotifiedmember(doNotifyingDTO.getMember());
		notify.setTitle((doNotifyingDTO.getTitle().equals(""))?"댓글입니다.":doNotifyingDTO.getTitle());
		notify.setNotifiedcontent(doNotifyingDTO.getContent());
		notify.setNotifiedcase(doNotifyingDTO.getWhy());
		notify.setNotifiedreason(doNotifyingDTO.getReason());
		notify.setRegdate(LocalDateTime.now());
		notify.setWritingid(doNotifyingDTO.getWritingId());
		notify.setCommentid(doNotifyingDTO.getCommentId());
		notify.setBoardid(doNotifyingDTO.getBoardId());
		notify.setDid("N");
		return notify;
	}


}
