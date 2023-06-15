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
@Table(name = "CookImage")
@Getter
@Setter
@ToString
public class CookImage {

	@Id
	@GeneratedValue
	private Long imageid;
	@JoinColumn(name = "writingid")
	@ManyToOne
	private CookBoard cookboard;
	private String imgname;
	private String oriname;
	private String url;
	private String repimage;

	public void saveCookImage(String ImgName, String OriName, String Url) {
		this.imgname = ImgName;
		this.oriname = OriName;
		this.url = Url;
	}

}
