package com.refrigerator.springboot.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Board")
@Getter
@Setter
@ToString
public class Board {
	
	@Id
	private Long boardid;
	private String boardname;
	
}
