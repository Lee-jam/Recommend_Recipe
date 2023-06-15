package com.refrigerator.springboot.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CookComment")
@Getter
@Setter
@ToString
public class CookComment {

	@Id
	@GeneratedValue
	private Long commentid;
	@JoinColumn(name = "mem_id")
	@ManyToOne
	private Member member;
	@JoinColumn(name = "writingid")
	@ManyToOne
	private CookBoard cookboard;
	private String content;
	private LocalDateTime regdate;
	@JoinColumn(name = "boardid")
	@ManyToOne
	private Board board;

	public static CookComment createookComment(Member member, CookBoard cookBoard, String Content, Board board) {
		CookComment cookComment = new CookComment();
		cookComment.setMember(member);
		cookComment.setCookboard(cookBoard);
		cookComment.setRegdate(LocalDateTime.now());
		cookComment.setBoard(board);
		cookComment.setContent(Content);
		return cookComment;
	}

}
