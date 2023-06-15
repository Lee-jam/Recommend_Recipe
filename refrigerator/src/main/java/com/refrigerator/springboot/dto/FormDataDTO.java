package com.refrigerator.springboot.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@JsonAutoDetect
public class FormDataDTO {

	private int count;
    private MultipartFile image;
	
}
