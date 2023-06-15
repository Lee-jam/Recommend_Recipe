package com.refrigerator.springboot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "RecipeFollowWriting")
@Getter
@Setter
@ToString
public class RecipeFollowWriting {

	@Id
	@GeneratedValue
	private Long followid;
	@ManyToOne
	@JoinColumn(name = "mem_id")
	private Member member;
	@ManyToOne
	@JoinColumn(name = "writingid")
	private RecipeBoard recipeboard;

}
