package com.refrigerator.springboot.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "TemporaryImage")
public class TemporaryImage {
	@Id
	@GeneratedValue
	private Long imageid;
	private String imgname;
	private String oriname;
	private String url;
	
	public void createImage(String imgName, String oriName, String url) {
		this.imgname = imgName;
		this.oriname = oriName;
		this.url = url;
	}
}
