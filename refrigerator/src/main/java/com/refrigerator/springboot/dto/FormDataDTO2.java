package com.refrigerator.springboot.dto;

import com.refrigerator.springboot.constant.NoticeCheck;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FormDataDTO2 {

	private String title;
	private String Content;
	private NoticeCheck noticeCheck;
	private MultipartFile repImage;
	
}
