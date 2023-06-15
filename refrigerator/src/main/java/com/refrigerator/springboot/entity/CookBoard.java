package com.refrigerator.springboot.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.refrigerator.springboot.dto.CookWritingDTO;
import com.refrigerator.springboot.constant.Delcheck;
import com.refrigerator.springboot.constant.NoticeCheck;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CookBoard")
@Getter
@Setter
@ToString
public class CookBoard {
	@Id
	@GeneratedValue
	private Long writingid;

	@ManyToOne
	@JoinColumn(name = "mem_id")
	private Member member;

	private int writingcount;

	private String title;

	@Column(length = 10000)
	private String content;

	@OneToMany(mappedBy = "cookboard")
	private List<CookImage> cookImages = new ArrayList<>();

	private LocalDateTime regdate;

	private int seencount;

	private int recomcount;

	@Enumerated(EnumType.STRING)
	private Delcheck delcheck;

	@Enumerated(EnumType.STRING)
	private NoticeCheck noticecheck;

	private int exceptdaily;

	private int dailycount;

	@ManyToOne
	@JoinColumn(name="boardid")
	private Board board;

	public static CookBoard createCookWriting(CookWritingDTO cookWritingDTO, Member member, Board board) {
		CookBoard cookBoard = new CookBoard();
		cookBoard.setMember(member);
		cookBoard.setTitle(cookWritingDTO.getTitle());
		cookBoard.setContent(cookWritingDTO.getContent());
		cookBoard.setRegdate(LocalDateTime.now());
		cookBoard.setSeencount(0);
		cookBoard.setRecomcount(0);
		cookBoard.setDelcheck(Delcheck.N);
		cookBoard.setNoticecheck(cookWritingDTO.getNoticeCheck());
		cookBoard.setExceptdaily(0);
		cookBoard.setDailycount(0);
		cookBoard.setBoard(board);
		return cookBoard;
	}

}
