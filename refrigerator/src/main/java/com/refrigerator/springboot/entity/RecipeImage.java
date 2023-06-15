package com.refrigerator.springboot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.refrigerator.springboot.constant.RepImage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "RecipeImage")
@Getter
@Setter
@ToString
public class RecipeImage {

	@Id
	@GeneratedValue
	private Long imageid;
	@JoinColumn(name = "writingid")
	@ManyToOne
	private RecipeBoard recipeboard;
	private String imgname;
	private String oriname;
	private String url;
	private String repimage;

	public void saveRecipeImage(String ImgName, String OriName, String Url) {
		System.out.println(repimage);
		this.imgname = ImgName;
		this.oriname = OriName;
		this.url = Url;
	}

}
