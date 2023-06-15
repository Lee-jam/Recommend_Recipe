package com.refrigerator.springboot.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "CookRecommned")
@Getter
@Setter
@ToString
public class CookRecommend {
	
	@Id
	@GeneratedValue
	private Long recomid;
	@ManyToOne
	@JoinColumn(name = "mem_id")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name= "writingid")
	private CookBoard cookboard;

}
