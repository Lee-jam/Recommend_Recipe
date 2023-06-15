package com.refrigerator.springboot.dto;

import lombok.Data;

@Data
public class VideoDTO {
    private String title;
    private String videoId;
    private String high_url;

    public VideoDTO(String title, String videoId, String high_url){
        this.title=title;
        this.videoId=videoId;
        this.high_url=high_url;
    }

}
